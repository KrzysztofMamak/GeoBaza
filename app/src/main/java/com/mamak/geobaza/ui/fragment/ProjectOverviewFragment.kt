package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.ui.adapter.ProjectDataAdapter
import com.mamak.geobaza.utils.manager.MappingManager
import kotlinx.android.synthetic.main.fragment_project_overview.*

class ProjectOverviewFragment(private val project: Project?) : Fragment() {
    private val projectDataAdapter = ProjectDataAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        setProjectDataAdapter(project)
    }

    private fun initRecycler() {
        rv_project_data.apply {
            adapter = projectDataAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setProjectDataAdapter(project: Project?) {
        projectDataAdapter.setFields(MappingManager.projectToFieldList(project))
    }

    private fun initStepbar() {
//        TODO managing stepbar
        project?.let {
            if (it.isMarked) {

            }
            if (it.isMeasured) {

            }
            if (it.isFinished) {

            }
        }
    }
}