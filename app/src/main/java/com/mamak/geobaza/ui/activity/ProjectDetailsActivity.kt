package com.mamak.geobaza.ui.activity

import android.Manifest
import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import com.github.buchandersenn.android_permission_manager.PermissionManager
import com.google.android.material.tabs.TabLayoutMediator
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.adapter.ProjectDetailsTabAdapter
import com.mamak.geobaza.base.BaseThemeActivityActionBar
import com.mamak.geobaza.viewmodel.ProjectDetailsActivityViewModel
import com.mamak.geobaza.utils.constans.AppConstans
import com.mamak.geobaza.utils.constans.AppConstans.ACCESS_TOKEN_MAPBOX
import com.mamak.geobaza.utils.constans.AppConstans.EXTRA_PROJECT_NUMBER
import com.mamak.geobaza.utils.manager.ThemeManager
import com.mapbox.mapboxsdk.Mapbox
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_details_project.*
import javax.inject.Inject

class ProjectDetailsActivity : BaseThemeActivityActionBar() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var  projectDetailsActivityViewModel: ProjectDetailsActivityViewModel

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
        projectDetailsActivityViewModel.getProjectFromDb(projectNumber)
        projectDetailsActivityViewModel.getProjectLiveData().observe(this, Observer {
            project = it
            setAdapter()
        })
    }

    private fun setAdapter() {
        projectDetailsTabAdapter =
            ProjectDetailsTabAdapter(project, this)
        vp_project_details.adapter = projectDetailsTabAdapter
        TabLayoutMediator(tl_project, vp_project_details,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.overview)
                    1 -> getString(R.string.map)
                    2 -> getString(R.string.sketch)
                    else -> getString(R.string.overview)
                }
            }
        ).attach()
    }

    private fun initViewModel() {
        projectDetailsActivityViewModel = viewModelFactory.create(projectDetailsActivityViewModel::class.java)
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
        supportActionBar?.apply {
            setBackgroundDrawable(ColorDrawable(getColor(
                ThemeManager.getColorResByAttr(this@ProjectDetailsActivity, R.attr.colorSecondary)
            )))
            elevation = 0F
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(false)
        }
    }

    override fun setStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = getColor(
                ThemeManager.getColorResByAttr(this@ProjectDetailsActivity, R.attr.colorSecondaryDark)
            )
        }
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