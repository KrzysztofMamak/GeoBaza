package com.mamak.geobaza.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.singleton.AreaLab
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.network.firebase.GeoBazaException.ErrorCode.CONNECT_EXCEPTION
import com.mamak.geobaza.network.firebase.GeoBazaException.ErrorCode.SOCKET_TIMEOUT_EXCEPTION
import com.mamak.geobaza.ui.`interface`.FilterDialogInterface
import com.mamak.geobaza.ui.`interface`.ProjectItemInterface
import com.mamak.geobaza.ui.activity.ProjectDetailsActivity
import com.mamak.geobaza.ui.activity.EntryActivity
import com.mamak.geobaza.ui.adapter.ProjectAdapter
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.ProjectListViewModel
import com.mamak.geobaza.utils.constans.AppConstans
import com.mamak.geobaza.utils.constans.AppConstans.DELAY_SHORT
import com.mamak.geobaza.utils.manager.LocationManager
import com.mamak.geobaza.utils.view.EmptyView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_project_list.*
import javax.inject.Inject

class ProjectListFragment : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var projectListViewModel: ProjectListViewModel

    private lateinit var projectAdapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
        getProjects()
        //activity.setActionBar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setComponents()
        getLocation()
    }

    private fun initViewModel() {
        projectListViewModel = viewModelFactory.create(ProjectListViewModel::class.java)
    }

    private fun setComponents() {
        setRecycler()
        setSwipeRefreshLayout()
    }

    private fun setRecycler() {
        projectAdapter = ProjectAdapter(createProjectItemInterface())
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rv_projects.apply {
            adapter = projectAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(itemDecoration)
        }
    }

    private fun setSwipeRefreshLayout() {
        srl_projects.setOnRefreshListener {
            ev_projects.visibility = View.GONE
            getProjects()
        }
    }

    private fun getProjects() {
        projectListViewModel.apply {
            fetchProjects()
            getProjectListLiveData().observe(this@ProjectListFragment, Observer { resource ->
                if (resource.isLoading) {
                    showProgressBar()
                } else if (!resource.data.isNullOrEmpty()) {
                    handleFetchingProjectSuccessResponse(resource.data)
                } else {
                    handleFetchingProjectErrorResponse(resource.exception)
                }
            })
        }
    }

    private fun handleFetchingProjectSuccessResponse(projectList: List<Project>) {
        setAreas(projectList.toMutableList())
        Handler().postDelayed({
            hideProgressBar()
            srl_projects.isRefreshing = false
            ev_projects.visibility = View.GONE
            projectAdapter.setProjects(projectList.toMutableList())
        }, DELAY_SHORT)
    }

    private fun handleFetchingProjectErrorResponse(geoBazaException: GeoBazaException?) {
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

    private fun showProgressBar() {
        ev_projects.visibility = View.GONE
        pb_projects.visibility = View.VISIBLE
        srl_projects.visibility = View.GONE
        rv_projects.visibility = View.GONE
    }

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
        filterDialogFragment.show(activity.supportFragmentManager, null)
    }

    private fun setAreas(projects: MutableList<Project>) {
        val areaList = mutableListOf<String>()
        projects.forEach {
            areaList.add(it.area)
        }
        AreaLab.setAreas(areaList)
    }

    private fun launchRegistrationLoginActivity() {
        val intent = Intent(activity, EntryActivity::class.java)
        startActivity(intent)
        activity.finish()
    }

    private fun launchProjectDetailsActivity(projectNumber: Int) {
        val intent = Intent(activity, ProjectDetailsActivity::class.java)
        intent.putExtra(AppConstans.EXTRA_PROJECT_NUMBER, projectNumber)
        startActivity(intent)
    }

    private fun createProjectItemInterface() = object : ProjectItemInterface {
        override fun navigate(x: Double, y: Double) {
            LocationManager.navigateByGeoCoordinates(activity, x, y)
        }

        override fun showMap(projectNumber: Int) {
            val action = ProjectListFragmentDirections
                    .actionProjectListFragmentToMapFragment(projectNumber)
            Navigation.findNavController(activity, R.id.nav_host_fragment)
                    .navigate(action)
        }

        override fun showDetails(projectNumber: Int) {
            launchProjectDetailsActivity(projectNumber)
        }
    }

    private fun createFilterDialogInterface(): FilterDialogInterface {
        return object : FilterDialogInterface {
            override fun filterProjects() {
                projectAdapter.filterProjects()
                activity.currentFocus?.clearFocus()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_project_list, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                projectAdapter.filter.filter(newText)
                return false
            }
        })
    }

//    TODO toolbar actions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> {
                showFilterDialog()
                return true
            }
            R.id.action_locate -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun getLocation() {
        projectListViewModel.getLocation()
        projectListViewModel.getLocationLiveData().observe(this@ProjectListFragment,
            Observer { resource ->
                if (resource.isSuccess && resource.data != null) {
                    Toast.makeText(context, resource.data.altitude.toString(), Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}