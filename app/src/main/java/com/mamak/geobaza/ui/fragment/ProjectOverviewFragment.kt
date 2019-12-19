package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.model.ProjectState
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.ProjectDetailsSharedViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_project_overview.*
import kotlinx.android.synthetic.main.item_list_property_edit_text.*
import kotlinx.android.synthetic.main.item_list_property_edit_text.et_property_value
import kotlinx.android.synthetic.main.item_list_property_edit_text.tv_property_name
import kotlinx.android.synthetic.main.item_list_property_edit_text.view.*
import kotlinx.android.synthetic.main.item_list_property_edit_text.view.tv_property_name
import kotlinx.android.synthetic.main.item_list_property_spinner.*
import kotlinx.android.synthetic.main.item_list_property_spinner.spinner_property_value
import kotlinx.android.synthetic.main.item_list_property_spinner.view.*
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
        return inflater.inflate(R.layout.fragment_project_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFields(project)
        setOnClicks()
    }

    private fun initViewModel() {
        projectDetailsSharedViewModel = viewModelFactory.create(ProjectDetailsSharedViewModel::class.java)
    }

    private fun setFields(project: Project) {
        setEditTextField(container_number, getString(R.string.project_number), project.number.toString())
        setEditTextField(container_area, getString(R.string.area), project.area)
        setEditTextField(container_town, getString(R.string.town), project.town)
        setEditTextField(container_street, getString(R.string.street), project.street)
        setEditTextField(container_points_count, getString(R.string.points_count), project.pointList.size.toString())

        var state = getString(R.string.received)
        setEditTextField(container_date_received, getString(R.string.date_received), project.receiveDate)
        if (project.state >= ProjectState.PROCESSED) {
            setEditTextField(container_date_processed, getString(R.string.date_processed), project.processDate)
            state = getString(R.string.processed)
        }
        if (project.state >= ProjectState.MARKED) {
            setEditTextField(container_date_marked, getString(R.string.date_marked), project.markDate)
            state = getString(R.string.marked)
        }
        if (project.state >= ProjectState.MEASURED) {
            setEditTextField(container_date_measured, getString(R.string.date_measured), project.measureDate)
            state = getString(R.string.measured)
        }
        if (project.state >= ProjectState.OUTLINED) {
            setEditTextField(container_date_outlined, getString(R.string.date_outlined), project.measureDate)
            state = getString(R.string.outlined)
        }
        if (project.state == ProjectState.FINISHED) {
            setEditTextField(container_date_finished, getString(R.string.date_finished), project.finishDate)
            state = getString(R.string.finished)
        }
        setSpinnerField(container_state, getString(R.string.state), state)
    }

    private fun setEditTextField(container: View, propertyName: String, propertyValue: String?) {
        container.apply {
            et_property_value.setText(propertyValue)
            tv_property_name.text = propertyName
            visibility = View.VISIBLE
        }
    }

    private fun setSpinnerField(container: View, propertyName: String, propertyValue: String?) {
        val index = spinner_property_value.getIndex(propertyValue)
        container.apply {
            spinner_property_value.setSelection(index)
            tv_property_name.text = propertyName
        }
    }

    private fun setListeners() {
        listOf<EditText>(
            container_area.et_property_value,
            container_town.et_property_value,
            container_street.et_property_value,
            container_date_received.et_property_value,
            container_date_processed.et_property_value,
            container_date_marked.et_property_value,
            container_date_measured.et_property_value,
            container_date_outlined.et_property_value,
            container_date_finished.et_property_value
        )
            .forEach {
                it.addTextChangedListener( object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {}
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        checkEquality()
                    }
                })
            }
    }

    private fun getNewProject() {
        val area = container_area.et_property_value.text
        val town = container_town.et_property_value.text
        val street = container_street.et_property_value.text
        val state = container_state.spinner_property_value.selectedItem
        val dateReceived = container_date_received.et_property_value.text
        val dateProcessed = container_date_processed.et_property_value.text
        val dateMarked = container_date_marked.et_property_value.text
        val dateMeasured = container_date_measured.et_property_value.text
        val dateOutlined = container_date_outlined.et_property_value.text
        val dateFinished = container_date_finished.et_property_value.text
    }

    private fun checkEquality() {

    }

    private fun setOnClicks() {
        iv_restore_project_data.setOnClickListener {
            setFields(project)
        }
        iv_save_project_data.setOnClickListener {
            updateProject()
        }
    }

    private fun updateProject() {
//        projectDetailsSharedViewModel.updateProject()
    }
}