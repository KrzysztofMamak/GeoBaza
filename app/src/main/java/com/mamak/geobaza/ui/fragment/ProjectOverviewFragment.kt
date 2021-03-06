package com.mamak.geobaza.ui.fragment

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.Observer
import com.mamak.geobaza.R
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.model.ProjectState
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.base.BaseFragment
import com.mamak.geobaza.utils.constans.AppConstans.DATE_DAY
import com.mamak.geobaza.utils.constans.AppConstans.DATE_MONTH
import com.mamak.geobaza.utils.constans.AppConstans.DATE_YEAR
import com.mamak.geobaza.viewmodel.ProjectOverviewViewModel
import com.mamak.geobaza.utils.fragment.DatePickerFragment
import com.mamak.geobaza.utils.manager.ThemeManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_project_overview.*
import kotlinx.android.synthetic.main.item_list_property_edit_text.view.*
import kotlinx.android.synthetic.main.item_list_property_spinner.spinner_property_value
import kotlinx.android.synthetic.main.item_list_property_spinner.view.*
import kotlinx.android.synthetic.main.item_list_property_text_view.view.*
import javax.inject.Inject

class ProjectOverviewFragment(private var project: Project) : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var projectOverviewViewModel: ProjectOverviewViewModel

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
        setSpinner()
        setFields()
        setListeners()
        setOnClicks()
    }

    private fun initViewModel() {
        projectOverviewViewModel = viewModelFactory.create(ProjectOverviewViewModel::class.java)
    }

    private fun setSpinner() {
        context?.let {
            val arrayAdapter = ArrayAdapter.createFromResource(it, R.array.array_state_overview, R.layout.item_spinner)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_property_value.adapter = arrayAdapter
        }
    }

    private fun setFields() {
        setTextViewField(container_number, getString(R.string.project_number), project.number.toString())
        setTextViewField(container_points_count, getString(R.string.points_count), project.pointList.size.toString())
        setTextViewField(container_date_received, getString(R.string.date_received), project.receiveDate)
        setTextViewField(container_date_processed, getString(R.string.date_processed), project.processDate)
        setTextViewField(container_date_marked, getString(R.string.date_marked), project.markDate)
        setTextViewField(container_date_measured, getString(R.string.date_measured), project.measureDate)
        setTextViewField(container_date_outlined, getString(R.string.date_outlined), project.outlineDate)
        setTextViewField(container_date_finished, getString(R.string.date_finished), project.finishDate)

        setEditTextField(container_note, getString(R.string.note), project.note)
        setEditTextField(container_area, getString(R.string.area), project.area)
        setEditTextField(container_town, getString(R.string.town), project.town)
        setEditTextField(container_street, getString(R.string.street), project.street)
        setEditTextField(container_description, getString(R.string.description), project.description)

        var state = getString(R.string.received)
        if (project.state >= ProjectState.PROCESSED) {
            state = getString(R.string.processed)
        }
        if (project.state >= ProjectState.MARKED) {
            state = getString(R.string.marked)
        }
        if (project.state >= ProjectState.MEASURED) {
            state = getString(R.string.measured)
        }
        if (project.state >= ProjectState.OUTLINED) {
            state = getString(R.string.outlined)
        }
        if (project.state == ProjectState.FINISHED) {
            state = getString(R.string.finished)
        }
        setSpinnerField(container_state, getString(R.string.state), state)
        setDateFieldsVisibility(project.state.ordinal)
    }

    private fun setTextViewField(container: View, propertyName: String, propertyValue: String?) {
        container.apply {
            tv_property_value.text = propertyValue
            tv_property_name.text = propertyName
        }
    }

    private fun setEditTextField(container: View, propertyName: String, propertyValue: String?) {
        container.apply {
            et_editable_property_value.setText(propertyValue)
            tv_editable_property_name.text = propertyName
            visibility = View.VISIBLE
        }
    }

    private fun setSpinnerField(container: View, propertyName: String, propertyValue: String?) {
        val index = spinner_property_value.getIndex(propertyValue)
        container.apply {
            spinner_property_value.setSelection(index)
            tv_spinner_property_name.text = propertyName
            visibility = View.VISIBLE
        }
    }

    private fun setListeners() {
        listOf<EditText>(
            container_area.et_editable_property_value,
            container_town.et_editable_property_value,
            container_street.et_editable_property_value,
            container_description.et_editable_property_value,
            container_note.et_editable_property_value
        ).forEach {
            it.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    checkEquality()
                }
            })
        }
        container_state.spinner_property_value.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                checkEquality()
                setDateFieldsVisibility(position)
            }
        }
    }

    private fun setDateFieldsVisibility(position: Int) {
        container_date_processed.visibility = if (position >= ProjectState.PROCESSED.ordinal) {
            View.VISIBLE
        } else {
            View.GONE
        }
        container_date_marked.visibility = if (position >= ProjectState.MARKED.ordinal) {
            View.VISIBLE
        } else {
            View.GONE
        }
        container_date_measured.visibility = if (position >= ProjectState.MEASURED.ordinal) {
            View.VISIBLE
        } else {
            View.GONE
        }
        container_date_outlined.visibility = if (position >= ProjectState.OUTLINED.ordinal) {
            View.VISIBLE
        } else {
            View.GONE
        }
        container_date_finished.visibility = if (position == ProjectState.FINISHED.ordinal) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun getNewProject(): Project {
        val number = project.number
        val area = container_area.et_editable_property_value.text.toString()
        val town = container_town.et_editable_property_value.text.toString()
        val street = container_street.et_editable_property_value.text.toString()
        val description = container_description.et_editable_property_value.text.toString()
        val points = project.pointList
        val index = container_state.spinner_property_value.selectedItem as String
        val state = ProjectState.values()[container_state.spinner_property_value.getIndex(index)]
        val dateReceived = container_date_received.tv_property_value.text.toString()
        val dateProcessed = container_date_processed.tv_property_value.text.toString()
        val dateMarked = container_date_marked.tv_property_value.text.toString()
        val dateMeasured = container_date_measured.tv_property_value.text.toString()
        val dateOutlined = container_date_outlined.tv_property_value.text.toString()
        val dateFinished = container_date_finished.tv_property_value.text.toString()
        val note = container_note.et_editable_property_value.text.toString()

        return Project(
            number, area, town, street, description, points, state, dateReceived,
            dateProcessed, dateMarked, dateMeasured, dateOutlined, dateFinished, note
        )
    }

    private fun checkEquality() {
        val hasDataChanged = project != getNewProject()
        val color = if (hasDataChanged) {
            context?.getColor(ThemeManager.getColorResByAttr(context, R.attr.colorSecondary))
        } else {
            context?.getColor(R.color.light_gray)
        }

        iv_restore_project_data.isEnabled = hasDataChanged
        iv_save_project_data.isEnabled = hasDataChanged

        color?.let {
            ImageViewCompat.setImageTintList(iv_restore_project_data, ColorStateList.valueOf(it))
            ImageViewCompat.setImageTintList(iv_save_project_data, ColorStateList.valueOf(it))
        }
    }

    private fun setOnClicks() {
        setTopOnClicks()
        setDateOnClicks()
    }

    private fun setTopOnClicks() {
        iv_restore_project_data.setOnClickListener {
            setFields()

        }
        iv_save_project_data.setOnClickListener {
            updateProject()
        }
    }

    private fun setDateOnClicks() {
        listOf<View>(
            container_date_received,
            container_date_processed,
            container_date_marked,
            container_date_measured,
            container_date_outlined,
            container_date_finished
        ).forEach { view ->
            view.setOnClickListener {
                val datePickerFragment = DatePickerFragment(
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        it.tv_property_value.text = getString(
                            R.string.template_date,
                            dayOfMonth.toString(),
                            (month + 1).toString(),
                            year.toString())
                    }
                )
                if (it.tv_property_value.text != "-") {
                    val args = Bundle()
                    val dateStrings = it.tv_property_value.text.toString().split(".")
                    args.putInt(DATE_DAY, dateStrings[0].toInt())
                    args.putInt(DATE_MONTH, dateStrings[1].toInt() - 1)
                    args.putInt(DATE_YEAR, dateStrings[2].toInt())
                    datePickerFragment.arguments = args
                }
                datePickerFragment.show(activity.supportFragmentManager, null)
            }
        }
    }

    private fun updateProject() {
        val newProject = getNewProject()
        projectOverviewViewModel.apply {
            updateProject(newProject)
            getProjectUpdateLiveData().observe(this@ProjectOverviewFragment, Observer { resource ->
                if (resource.isSuccess) {
                    handleProjectUpdateSuccessResponse()
                } else {
                    handleProjectUpdateErrorResponse()
                }
            })
        }
    }

    private fun handleProjectUpdateSuccessResponse() {
        Toast.makeText(context, getString(R.string.project_update_success), Toast.LENGTH_SHORT).show()
    }

    private fun handleProjectUpdateErrorResponse() {
        Toast.makeText(context, getString(R.string.project_update_error), Toast.LENGTH_SHORT).show()
    }
}