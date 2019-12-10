package com.mamak.geobaza.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.buchandersenn.android_permission_manager.PermissionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.singleton.AreaLab
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.network.firebase.GeoBazaException.ErrorCode.CONNECT_EXCEPTION
import com.mamak.geobaza.network.firebase.GeoBazaException.ErrorCode.SOCKET_TIMEOUT_EXCEPTION
import com.mamak.geobaza.ui.`interface`.FilterDialogInterface
import com.mamak.geobaza.ui.`interface`.ProjectListItemInterface
import com.mamak.geobaza.ui.adapter.ProjectListAdapter
import com.mamak.geobaza.ui.base.BaseActivity
import com.mamak.geobaza.ui.base.BaseThemeActivityNoActionBar
import com.mamak.geobaza.ui.fragment.FilterDialogFragment
import com.mamak.geobaza.ui.viewmodel.ProjectListViewModel
import com.mamak.geobaza.utils.constans.AppConstans.DELAY_SHORT
import com.mamak.geobaza.utils.constans.AppConstans.EXTRA_PROJECT_NUMBER
import com.mamak.geobaza.utils.constans.AppConstans.REQUEST_CODE_ACCESS_FINE_LOCATION
import com.mamak.geobaza.utils.manager.LocationManager
import com.mamak.geobaza.utils.view.EmptyView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_list_project.*
import javax.inject.Inject

class ProjectListActivity : BaseThemeActivityNoActionBar(),
        ActivityCompat.OnRequestPermissionsResultCallback,
        NavigationView.OnNavigationItemSelectedListener {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var  projectListViewModel: ProjectListViewModel

    private lateinit var projectListAdapter: ProjectListAdapter
    private var permissionManager = PermissionManager.create(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_project)
        AndroidInjection.inject(this)
        checkPermissionsAndShotLocation()
        initComponents()
        initViewModel()
        getProjects()
    }

    private fun initViewModel() {
        projectListViewModel = viewModelFactory.create(projectListViewModel::class.java)
    }

    private fun initComponents() {
        setNavigation()
        initRecycler()
        initSwipeRefreshLayout()
    }

    private fun initRecycler() {
        projectListAdapter = ProjectListAdapter(createProjectListItemInterface())
        rv_projects.apply {
            adapter = projectListAdapter
            layoutManager = LinearLayoutManager(this@ProjectListActivity)
        }
    }

    private fun setNavigation() {
        setSupportActionBar(tb_projects)

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            tb_projects,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nv_projects.setNavigationItemSelectedListener(this)
    }

    private fun initSwipeRefreshLayout() {
        srl_projects.setOnRefreshListener {
            getProjects()
        }
    }

    private fun getProjects() {
        projectListViewModel.apply {
            fetchProjectsByRepo()
            getProjectsLiveData().observe(this@ProjectListActivity, Observer { resource ->
                if (resource.isLoading) {
                    showProgressBar()
                } else if (!resource.data.isNullOrEmpty()) {
                    handleFetchingProjectsSuccessResponse(resource.data)
                } else {
                    handleFetchingProjectsErrorResponse(resource.exception)
                }
            })
        }
    }

    private fun handleFetchingProjectsSuccessResponse(projects: List<Project>) {
        setAreas(projects.toMutableList())
        Handler().postDelayed({
            hideProgressBar()
            srl_projects.isRefreshing = false
            ev_projects.visibility = View.GONE
            projectListAdapter.setProjects(projects.toMutableList())
        }, DELAY_SHORT)
    }

    private fun handleFetchingProjectsErrorResponse(geoBazaException: GeoBazaException?) {
        hideProgressBar()
        srl_projects.isRefreshing = false
        setEmptyViewOnClick()
        if (geoBazaException != null) {
            when (geoBazaException.errorCode) {
                SOCKET_TIMEOUT_EXCEPTION -> {
                    ev_projects.draw(EmptyView.Type.NO_SERVICE)
                }
                CONNECT_EXCEPTION -> {
                    ev_projects.draw(EmptyView.Type.NO_INTERNET)
                }
                else -> {
                    ev_projects.draw(EmptyView.Type.NO_FEEDBACK)
                }
            }
        } else {
            ev_projects.draw(EmptyView.Type.NO_DATA)
        }
        ev_projects.visibility = View.VISIBLE
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
            projectListViewModel.apply {
                googleSignOut(googleSignInClient)
                getGoogleSignOutLiveData().observe(this@ProjectListActivity, Observer { resource ->
                    when {
                        resource.isLoading -> {}
                        resource.isSuccess -> launchRegistrationLoginActivity()
                        else -> handleSignOutErrorResponse(resource.exception)
                    }
                })
            }
        } else {
            handleSignOutSuccessResponse()
        }
    }

    private fun handleSignOutSuccessResponse() {
        launchRegistrationLoginActivity()
    }

    private fun handleSignOutErrorResponse(geoBazaException: GeoBazaException?) {
        if (geoBazaException != null) {
            when (geoBazaException.errorCode) {}
        } else {}
    }

//    TODO Refactor
    private fun showProgressBar() {
        pb_projects.visibility = View.VISIBLE
        srl_projects.visibility = View.GONE
        rv_projects.visibility = View.GONE
    }

//    TODO Refactor
    private fun hideProgressBar() {
        pb_projects.visibility = View.GONE
        srl_projects.visibility = View.VISIBLE
        rv_projects.visibility = View.VISIBLE
    }

    private fun setEmptyViewOnClick() {
        ev_projects.setOnClickListener {
            getProjects()
            it.visibility = View.GONE
        }
    }

    private fun showFilterDialog() {
        val filterDialogFragment = FilterDialogFragment(createFilterDialogInterface())
        filterDialogFragment.show(supportFragmentManager, null)
    }

    private fun setAreas(projects: MutableList<Project>) {
        val areaList = mutableListOf<String>()
        projects.forEach {
            areaList.add(it.area)
        }
        AreaLab.setAreas(areaList)
    }

    private fun launchRegistrationLoginActivity() {
        val intent = Intent(this, RegistrationLoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun launchProjectDetailsActivity(projectNumber: Int) {
        val intent = Intent(this, ProjectDetailsActivity::class.java)
        intent.putExtra(EXTRA_PROJECT_NUMBER, projectNumber)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_list_actionbar, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                projectListAdapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_filter -> showFilterDialog()
            R.id.action_locate -> projectListAdapter.notifyDataSetChanged()
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_travel_planner -> {}
            R.id.nav_statistics -> {}
            R.id.nav_setting -> {}
            R.id.nav_sign_out -> signOut()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun createProjectListItemInterface() = object : ProjectListItemInterface {
        override fun openGoogleMaps(x: Double, y: Double) {
            LocationManager.navigateByGeoCoordinates(this@ProjectListActivity, x, y)
        }

        override fun openProjectDetails(projectNumber: Int) {
            launchProjectDetailsActivity(projectNumber)
        }
    }

    private fun createFilterDialogInterface(): FilterDialogInterface {
        return object : FilterDialogInterface {
            override fun filterProjects() {
                projectListAdapter.filterProjects()
                this@ProjectListActivity.currentFocus?.clearFocus()
            }
        }
    }

    private fun checkPermissionsAndShotLocation() {
        permissionManager.with(Manifest.permission.ACCESS_FINE_LOCATION)
            .usingRequestCode(REQUEST_CODE_ACCESS_FINE_LOCATION)
            .onPermissionGranted { projectListViewModel.shotLocation() }
            .request()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionManager.handlePermissionResult(requestCode, grantResults)
    }
}