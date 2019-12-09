package com.mamak.geobaza.ui.activity

import android.Manifest
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import com.github.buchandersenn.android_permission_manager.PermissionManager
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.adapter.ProjectDetailsTabAdapter
import com.mamak.geobaza.ui.base.BaseThemeActivityActionBar
import com.mamak.geobaza.ui.fragment.ProjectMapFragment
import com.mamak.geobaza.ui.fragment.ProjectOverviewFragment
import com.mamak.geobaza.ui.fragment.ProjectSketchFragment
import com.mamak.geobaza.ui.viewmodel.ProjectDetailsSharedViewModel
import com.mamak.geobaza.utils.constans.AppConstans
import com.mamak.geobaza.utils.constans.AppConstans.ACCESS_TOKEN_MAPBOX
import com.mamak.geobaza.utils.constans.AppConstans.EXTRA_PROJECT_NUMBER
import com.mapbox.mapboxsdk.Mapbox
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_details_project.*
import javax.inject.Inject
import com.mamak.geobaza.utils.manager.AttributesManager


class ProjectDetailsActivity : BaseThemeActivityActionBar() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var  projectDetailsSharedViewModel: ProjectDetailsSharedViewModel

    private lateinit var projectDetailsTabAdapter: ProjectDetailsTabAdapter
    private lateinit var project: Project
    private val permissionManager = PermissionManager.create(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, ACCESS_TOKEN_MAPBOX)
        setContentView(R.layout.activity_details_project)
        AndroidInjection.inject(this)
        checkPermissions()
        initViewModel()
        setCurrentProject()
    }

    private fun setCurrentProject() {
        val projectNumber = intent.getIntExtra(EXTRA_PROJECT_NUMBER, 1)
        projectDetailsSharedViewModel.getProjectFromDb(projectNumber)
        projectDetailsSharedViewModel.getProjectLiveData().observe(this, Observer {
            project = it
            setAdapter()
        })
    }

    private fun setAdapter() {
        projectDetailsTabAdapter = ProjectDetailsTabAdapter(supportFragmentManager)
        projectDetailsTabAdapter.apply {
            addFragment(ProjectOverviewFragment(project), getString(R.string.overview))
            addFragment(ProjectMapFragment(project), getString(R.string.map))
            addFragment(ProjectSketchFragment(), getString(R.string.sketch))
        }
        vp_project.adapter = projectDetailsTabAdapter
        tl_project.setupWithViewPager(vp_project)
    }

    private fun initViewModel() {
        projectDetailsSharedViewModel = viewModelFactory.create(projectDetailsSharedViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setActionBarColor() {
//        TODO COLORRR
//        supportActionBar?.apply {
//            setBackgroundDrawable(ColorDrawable(getColor(
//                AttributesManager.getColorResByAttr(theme, R.attr.colorSecondary)
//            )))
//            elevation = 0F
//            setDisplayHomeAsUpEnabled(true)
//            setDisplayShowHomeEnabled(false)
//        }
    }

    override fun setStatusBar() {
//        TODO COLORRR
//        window.apply {
//            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            statusBarColor = getColor(
//                AttributesManager.getColorResByAttr(theme, R.attr.colorSecondaryDark)
//            )
//        }
    }

    private fun checkPermissions() {
        permissionManager.with(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE)
            .usingRequestCode(AppConstans.REQUEST_CODE_FINE_OMS_PERMISSIONS)
            .request()
    }
}