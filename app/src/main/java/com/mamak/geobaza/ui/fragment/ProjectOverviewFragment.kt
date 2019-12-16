package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.ProjectDetailsSharedViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_project_overview_new.*
import kotlinx.android.synthetic.main.item_list_property.view.*
import javax.inject.Inject

class ProjectOverviewFragment(private val project: Project) : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var projectDetailsSharedViewModel: ProjectDetailsSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project_overview_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFields()
    }

    private fun initViewModel() {
        projectDetailsSharedViewModel = viewModelFactory.create(ProjectDetailsSharedViewModel::class.java)
    }

    private fun setFields() {
        container_number.apply {
            tv_property_name.text = getString(R.string.project_number)
            tv_property_value.text = project.number.toString()
        }
        container_area.apply {
            tv_property_name.text = getString(R.string.area)
            tv_property_value.text = project.area
        }
        container_address.apply {
            tv_property_name.text = getString(R.string.address)
            tv_property_value.text = getString(R.string.address_input, project.town, project.street)
        }
        container_points_count.apply {
            tv_property_name.text = getString(R.string.points_count)
            tv_property_value.text = project.pointList.size.toString()
        }

        container_date_processed.tv_property_name.text = getString(R.string.date_processed)
        container_date_marked.tv_property_name.text = getString(R.string.date_marked)
        container_date_measured.tv_property_name.text = getString(R.string.date_measured)
        container_date_finished.tv_property_name.text = getString(R.string.date_finished)

        var state = getString(R.string.received)
        if (project.isProcessed) {
            state = getString(R.string.processed)
            container_date_processed.apply {
                tv_property_value.text = project.processDate
                visibility = View.VISIBLE
            }
        }
        if (project.isMarked) {
            state = getString(R.string.marked)
            container_date_marked.apply {
                tv_property_value.text = project.markDate
                visibility = View.VISIBLE
            }
        }
        if (project.isMeasured) {
            state = getString(R.string.measured)
            container_date_measured.apply {
                tv_property_value.text = project.measureDate
                visibility = View.VISIBLE
            }
        }
        if (project.isFinished) {
            state = getString(R.string.finished)
            container_date_finished.apply {
                tv_property_value.text = project.finishDate
                visibility = View.VISIBLE
            }
        }
        container_state.apply {
            tv_property_name.text = getString(R.string.state)
            tv_property_value.text = state
        }
    }
}