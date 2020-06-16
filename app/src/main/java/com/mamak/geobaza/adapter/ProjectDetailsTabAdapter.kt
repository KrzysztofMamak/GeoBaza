package com.mamak.geobaza.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.ui.fragment.ProjectMapFragment
import com.mamak.geobaza.ui.fragment.ProjectOverviewFragment
import com.mamak.geobaza.ui.fragment.ProjectSketchFragment

class ProjectDetailsTabAdapter(
    private val project: Project,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentsCount = 3
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProjectOverviewFragment(project)
            1 -> ProjectMapFragment(project)
            2 -> ProjectSketchFragment()
            else -> ProjectOverviewFragment(project)
        }
    }

    override fun getItemCount() = fragmentsCount
}