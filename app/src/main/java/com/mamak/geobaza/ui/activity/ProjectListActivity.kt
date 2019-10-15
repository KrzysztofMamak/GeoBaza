package com.mamak.geobaza.ui.activity

import android.Manifest
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.buchandersenn.android_permission_manager.PermissionManager
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.singleton.ProjectLab
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.`interface`.FilterDialogInterface
import com.mamak.geobaza.ui.`interface`.ProjectListItemInterface
import com.mamak.geobaza.ui.adapter.ProjectListAdapter
import com.mamak.geobaza.ui.base.BaseActivity
import com.mamak.geobaza.ui.fragment.FilterDialogFragment
import com.mamak.geobaza.ui.viewmodel.ProjectListViewModel
import com.mamak.geobaza.utils.AppConstans.REQUEST_CODE_ACCESS_FINE_LOCATION
import com.mamak.geobaza.utils.EmptyView
import com.mamak.geobaza.utils.LocationManager
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_list_project.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ProjectListActivity : BaseActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var  projectListViewModel: ProjectListViewModel
    @Inject
    internal lateinit var picasso: Picasso
    private lateinit var projectListAdapter: ProjectListAdapter
    private var permissionManager = PermissionManager.create(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_project)
        AndroidInjection.inject(this)
        checkPermissionsAndShotLocation()
        initRecycler()
        initViewModel()
        getProjects()
    }

    private fun initViewModel() {
        projectListViewModel = viewModelFactory.create(projectListViewModel::class.java)
    }

    private fun initRecycler() {
        projectListAdapter = ProjectListAdapter(createProjectListItemInterface())
        rv_projects.apply {
            adapter = projectListAdapter
            layoutManager = LinearLayoutManager(this@ProjectListActivity)
        }
    }

    private fun getProjects() {
        projectListViewModel.fetchProjects()
        projectListViewModel.getProjectsLiveData().observe(this, Observer { resource ->
            if (resource!!.isLoading) {
                showProgressBar()
            } else if (!resource.data.isNullOrEmpty()) {
                handleSuccessResponse(resource.data)
            } else {
                handleErrorResponse(resource.exception)
            }
        })
    }

    private fun handleSuccessResponse(projects: List<Project>) {
        hideProgressBar()
        ev_project_list.visibility = View.GONE
        ProjectLab.setProjects(projects).also {
            projectListAdapter.setProjects()
        }
    }

    private fun handleErrorResponse(exception: Exception? = null) {
        hideProgressBar()
        when (exception) {
            is SocketTimeoutException -> {
                showEmptyView(EmptyView.Companion.EmptyViewType.NO_SERVICE)
            }
            is ConnectException -> {
                showEmptyView(EmptyView.Companion.EmptyViewType.NO_INTERNET)
            }
            else -> {
                showEmptyView(EmptyView.Companion.EmptyViewType.NO_DATA)
            }
        }
    }

    private fun showProgressBar() {
        pb_projects.visibility = View.VISIBLE
        rv_projects.visibility = View.GONE
    }

    private fun hideProgressBar() {
        pb_projects.visibility = View.GONE
        rv_projects.visibility = View.VISIBLE
    }

    private fun showEmptyView(emptyViewType: EmptyView.Companion.EmptyViewType) {
        setEmptyViewOnClick()
        when (emptyViewType) {
            EmptyView.Companion.EmptyViewType.NO_DATA -> {
                ev_project_list.showNoDataEmptyView()
            }
            EmptyView.Companion.EmptyViewType.NO_INTERNET -> {
                ev_project_list.showNoInternetEmptyView()
            }
            EmptyView.Companion.EmptyViewType.NO_SERVICE -> {
                ev_project_list.showNoServiceEmptyView()
            }
        }
        ev_project_list.visibility = View.VISIBLE
    }

    private fun setEmptyViewOnClick() {
        ev_project_list.setOnClickListener {
            getProjects()
            it.visibility = View.GONE
        }
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
//            TODO check - updating list after location change
            R.id.action_locate -> projectListAdapter.notifyDataSetChanged()
        }
        return true
    }

    private fun showFilterDialog() {
        val filterDialogFragment = FilterDialogFragment(createFilterDialogInterface())
        filterDialogFragment.show(supportFragmentManager, null)
    }

    private fun createProjectListItemInterface() = object : ProjectListItemInterface {
        override fun openGoogleMaps(x: Double, y: Double) {
            LocationManager.navigateByGeoCoordinates(this@ProjectListActivity, x, y)
        }

        override fun openProjectDetails(number: Int?) {}
    }

    private fun createFilterDialogInterface(): FilterDialogInterface {
        return object : FilterDialogInterface {
            override fun filterProjects() {
                projectListAdapter.filterProjects()
//                TODO -  Hide SearchView, Refactor
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