package com.mamak.geobaza.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ProjectDetailsTabAdapter(fragmentManager: FragmentManager)
        : FragmentStatePagerAdapter(fragmentManager) {
    private var fragmentList = mutableListOf<Fragment>()
    private var titleList = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getItem(position: Int) = fragmentList[position]

    override fun getPageTitle(position: Int) = titleList[position]

    override fun getCount() = fragmentList.size
}