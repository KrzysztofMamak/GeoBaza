package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.model.ProjectState
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
        setOnClicks()
    }

    private fun initViewModel() {
        projectDetailsSharedViewModel = viewModelFactory.create(ProjectDetailsSharedViewModel::class.java)
    }

    private fun setFields() {
        setField(container_number, getString(R.string.project_number), project.number.toString())
        setField(container_area, getString(R.string.area), project.area)
        setField(container_address, getString(R.string.address), getString(R.string.address_input, project.town, project.street))
        setField(container_points_count, getString(R.string.points_count), project.pointList.size.toString())

        var state = getString(R.string.received)
        setField(container_date_received, getString(R.string.date_received), project.receiveDate)
        if (project.state >= ProjectState.PROCESSED) {
            setField(container_date_processed, getString(R.string.date_processed), project.processDate)
            state = getString(R.string.processed)
        }
        if (project.state >= ProjectState.MARKED) {
            setField(container_date_marked, getString(R.string.date_marked), project.markDate)
            state = getString(R.string.marked)
        }
        if (project.state >= ProjectState.MEASURED) {
            setField(container_date_measured, getString(R.string.date_measured), project.measureDate)
            state = getString(R.string.measured)
        }
        if (project.state >= ProjectState.OUTLINED) {
            setField(container_date_outlined, getString(R.string.date_outlined), project.measureDate)
            state = getString(R.string.outlined)
        }
        if (project.state == ProjectState.FINISHED) {
            setField(container_date_finished, getString(R.string.date_finished), project.finishDate)
            state = getString(R.string.finished)
        }
        setField(container_state, getString(R.string.state), state)
    }

    private fun setField(container: View, propertyName: String, propertyValue: String?) {
        container.apply {
            tv_property_value.text = propertyValue
            tv_property_name.text = propertyName
            visibility = View.VISIBLE
        }
    }

    private fun setOnClicks() {

    }

    private fun showTextEditDialog() {

    }

    private fun showChoiceDialog() {

    }

    private fun updateProject() {

//        projectDetailsSharedViewModel.updateProject()
    }
}