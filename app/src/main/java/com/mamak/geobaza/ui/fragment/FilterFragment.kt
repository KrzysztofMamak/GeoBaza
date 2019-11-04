package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.`interface`.FilterDialogInterface
import com.mamak.geobaza.utils.manager.ProjectListManager
import kotlinx.android.synthetic.main.fragment_filter_project_list.*

class FilterDialogFragment(private val filterDialogInterface: FilterDialogInterface) : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter_project_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setAdapters()
        setSpinners()
        setOnClicks()
    }

    private fun createArrayAdapter(itemsArray: Int) : ArrayAdapter<CharSequence>? {
        var arrayAdapter: ArrayAdapter<CharSequence>? = null
        context?.let {
            arrayAdapter = ArrayAdapter.createFromResource(it, itemsArray, R.layout.item_spinner)
            (arrayAdapter as ArrayAdapter<CharSequence>).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        return arrayAdapter
    }

    private fun setAdapters() {
//        TODO manage via database
        spinner_area.adapter = createArrayAdapter(R.array.array_area_filter)
        spinner_state.adapter = createArrayAdapter(R.array.array_state_filter)
        spinner_sort_type.adapter = createArrayAdapter(R.array.array_sort_type_filter)
        spinner_sort_order.adapter = createArrayAdapter(R.array.array_sort_order_filter)
    }

    private fun setSpinners() {
        setAreaSpinner()
        setStateSpinner()
        setSortTypeSpinner()
        setSortOrderSpinner()
    }

    private fun setAreaSpinner() {
        val index = if (ProjectListManager.area == ProjectListManager.ALL_AREAS) {
            spinner_area.getIndex(getString(R.string.all))
        } else {
            spinner_area.getIndex(ProjectListManager.area)
        }
        spinner_area.setSelection(index)
    }

    private fun setStateSpinner() {
        val position: Int = when (ProjectListManager.state) {
            ProjectListManager.State.PROCESSED -> spinner_state.getIndex(getString(R.string.processed))
            ProjectListManager.State.MARKED -> spinner_state.getIndex(getString(R.string.marked))
            ProjectListManager.State.MEASURED -> spinner_state.getIndex(getString(R.string.measured))
            ProjectListManager.State.DONE -> spinner_state.getIndex(getString(R.string.finished))
            else -> spinner_state.getIndex(getString(R.string.all))
        }
        spinner_state.setSelection(position)
    }

    private fun setSortTypeSpinner() {
        val position: Int = when (ProjectListManager.sort) {
            ProjectListManager.Sort.NUMBER -> spinner_sort_type.getIndex(getString(R.string.number))
            ProjectListManager.Sort.DISTANCE -> spinner_sort_type.getIndex(getString(R.string.distance))
            ProjectListManager.Sort.ALPHABET -> spinner_sort_type.getIndex(getString(R.string.alphabet))
            else -> spinner_sort_type.getIndex(getString(R.string.complexity))
        }
        spinner_sort_type.setSelection(position)
    }

    private fun setSortOrderSpinner() {
        val position: Int = when (ProjectListManager.order) {
            ProjectListManager.Order.INCREASE -> spinner_sort_order.getIndex(getString(R.string.increase))
            else -> spinner_sort_order.getIndex(getString(R.string.decrease))
        }
        spinner_sort_order.setSelection(position)
    }

    private fun setOnClicks() {
        tv_cancel_choice.setOnClickListener {
            this@FilterDialogFragment.dismiss()
        }
        tv_apply_choice.setOnClickListener {
            applyChanges()
            dismiss()
        }
    }

    private fun applyChanges() {
        val area = applyAreaChanges()
        val state = applyStateChanges()
        val sort = applySortChanges()
        val order = applyOrderChanges()

        ProjectListManager.setAttributes(area, state, sort, order)
        filterDialogInterface.filterProjects()
    }

    private fun applyAreaChanges(): String {
        val area = spinner_area.selectedItem as String
        return if (area == getString(R.string.all)) {
            ProjectListManager.ALL_AREAS
        } else {
            area
        }
    }

    private fun applyStateChanges(): ProjectListManager.State {
        return when (spinner_state.selectedItem) {
            getString(R.string.processed) -> ProjectListManager.State.PROCESSED
            getString(R.string.marked) -> ProjectListManager.State.MARKED
            getString(R.string.measured) -> ProjectListManager.State.MEASURED
            getString(R.string.finished) -> ProjectListManager.State.DONE
            else -> ProjectListManager.State.ALL
        }
    }

    private fun applySortChanges(): ProjectListManager.Sort {
        return when (spinner_sort_type.selectedItem) {
            getString(R.string.complexity) -> ProjectListManager.Sort.COMPLEXITY
            getString(R.string.distance) -> ProjectListManager.Sort.DISTANCE
            getString(R.string.alphabet) -> ProjectListManager.Sort.ALPHABET
            else -> ProjectListManager.Sort.NUMBER
        }
    }

    private fun applyOrderChanges(): ProjectListManager.Order {
        return when (spinner_sort_order.selectedItem) {
            getString(R.string.decrease) -> ProjectListManager.Order.DECREASE
            else -> ProjectListManager.Order.INCREASE
        }
    }
}