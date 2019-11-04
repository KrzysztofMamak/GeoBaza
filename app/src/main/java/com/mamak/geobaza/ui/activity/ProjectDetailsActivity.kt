package com.mamak.geobaza.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.singleton.ProjectLab
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.adapter.ProjectDetailsTabAdapter
import com.mamak.geobaza.ui.base.BaseActivity
import com.mamak.geobaza.ui.fragment.ProjectMapFragment
import com.mamak.geobaza.ui.fragment.ProjectOverviewFragment
import com.mamak.geobaza.ui.fragment.ProjectSketchFragment
import com.mamak.geobaza.ui.viewmodel.ProjectDetailsSharedViewModel
import com.mamak.geobaza.ui.viewmodel.ProjectListViewModel
import com.mamak.geobaza.utils.constans.AppConstans.ACCESS_TOKEN_MAPBOX
import com.mamak.geobaza.utils.constans.AppConstans.EXTRA_PROJECT_NUMBER
import com.mapbox.mapboxsdk.Mapbox
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_details_project.*
import javax.inject.Inject

class ProjectDetailsActivity : BaseActivity() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var  projectDetailsSharedViewModel: ProjectDetailsSharedViewModel

    private lateinit var projectDetailsTabAdapter: ProjectDetailsTabAdapter
    private var project: Project? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, ACCESS_TOKEN_MAPBOX)
        setContentView(R.layout.activity_details_project)
        AndroidInjection.inject(this)
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

    override fun setActionBarColor() {
        supportActionBar?.apply {
            setBackgroundDrawable(ColorDrawable(getColor(R.color.colorSecondary)))
            elevation = 0F
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(false)
        }
    }

    override fun setStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(this@ProjectDetailsActivity, R.color.colorSecondaryDark)
        }
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
}