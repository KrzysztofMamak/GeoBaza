package com.mamak.geobaza.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.singleton.ProjectLab
import com.mamak.geobaza.ui.adapter.ProjectDetailsTabAdapter
import com.mamak.geobaza.ui.base.BaseActivity
import com.mamak.geobaza.ui.fragment.ProjectMapFragment
import com.mamak.geobaza.ui.fragment.ProjectOverviewFragment
import com.mamak.geobaza.ui.fragment.ProjectSketchFragment
import com.mamak.geobaza.utils.constans.AppConstans
import com.mamak.geobaza.utils.constans.AppConstans.EXTRA_PROJECT_NUMBER
import kotlinx.android.synthetic.main.activity_details_project.*

//TODO getting project from db via viewmodel and manage it in fragments
class ProjectDetailsActivity : BaseActivity() {
    private lateinit var projectDetailsTabAdapter: ProjectDetailsTabAdapter
    private var project: Project? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_project)
        setCurrentProject()
        setAdapter()
    }

    private fun setCurrentProject() {
        val projectNumber = intent.getIntExtra(EXTRA_PROJECT_NUMBER, 1)
        project = ProjectLab.getProject(projectNumber)
    }

    private fun setAdapter() {
        projectDetailsTabAdapter = ProjectDetailsTabAdapter(supportFragmentManager)
        projectDetailsTabAdapter.apply {
            addFragment(ProjectOverviewFragment(project), getString(R.string.overview))
            addFragment(ProjectMapFragment(), getString(R.string.map))
            addFragment(ProjectSketchFragment(), getString(R.string.sketch))
        }
        vp_project.adapter = projectDetailsTabAdapter
        tl_project.setupWithViewPager(vp_project)
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