package com.mamak.geobaza.ui.activity

import android.os.Bundle
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.adapter.ProjectDetailsTabAdapter
import com.mamak.geobaza.ui.base.BaseActivity
import com.mamak.geobaza.ui.fragment.ProjectMapFragment
import com.mamak.geobaza.ui.fragment.ProjectOverviewFragment
import com.mamak.geobaza.ui.fragment.ProjectSketchFragment
import kotlinx.android.synthetic.main.activity_details_project.*

class ProjectDetailsActivity : BaseActivity() {
    private lateinit var projectDetailsTabAdapter: ProjectDetailsTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_project)
        setAdapter()
    }

    private fun setAdapter() {
        projectDetailsTabAdapter = ProjectDetailsTabAdapter(supportFragmentManager)
        projectDetailsTabAdapter.apply {
            addFragment(ProjectOverviewFragment(), getString(R.string.overview))
            addFragment(ProjectMapFragment(), getString(R.string.map))
            addFragment(ProjectSketchFragment(), getString(R.string.sketch))
        }
        vp_project.adapter = projectDetailsTabAdapter
        tl_project.setupWithViewPager(vp_project)
    }
}