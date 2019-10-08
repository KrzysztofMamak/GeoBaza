package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.`interface`.FilterDialogInterface
import com.mamak.geobaza.utils.ProjectListManager
import kotlinx.android.synthetic.main.fragment_filter_project_list.*

//TODO Refactor, add SpinnerHelper, Use resources as strings
class FilterDialogFragment(private val filterDialogInterface: FilterDialogInterface) : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter_project_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setAdapters()
        setSpinners()
        setOnClicks()
    }

    private fun createArrayAdapter(itemsArray: Int) : ArrayAdapter<CharSequence> {
//        TODO Context? -> Context
        val arrayAdapter = ArrayAdapter.createFromResource(context, itemsArray, R.layout.item_spinner)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return arrayAdapter
    }

    private fun setAdapters() {
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
        spinner_area.setSelection(
            when (ProjectListManager.area) {
                "Cieszyn" -> 0
                "Jastrzębie-Zdrój" -> 1
                "Pszczyna" -> 2
                "Rybnik" -> 3
                "Wodzisław Śląski" -> 4
                "Żory" -> 5
                else -> 6
            }
        )
    }

    private fun setStateSpinner() {
        spinner_state.setSelection(
            when (ProjectListManager.state) {
                ProjectListManager.State.MARKED -> 0
                ProjectListManager.State.MEASURED -> 1
                ProjectListManager.State.DONE -> 2
                else -> 3
            }
        )
    }

    private fun setSortTypeSpinner() {
        spinner_sort_type.setSelection(
            when (ProjectListManager.sort) {
                ProjectListManager.Sort.NUMBER -> 0
                ProjectListManager.Sort.DISTANCE -> 1
                ProjectListManager.Sort.ALPHABET -> 2
                else -> 3
            }
        )
    }

    private fun setSortOrderSpinner() {
        spinner_sort_order.setSelection(
            when (ProjectListManager.order) {
                ProjectListManager.Order.INCREASE -> 0
                else -> 1
            }
        )
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
        return spinner_area.selectedItem as String
    }

    private fun applyStateChanges(): ProjectListManager.State {
        return when (spinner_state.selectedItem) {
            "Marked" -> ProjectListManager.State.MARKED
            "Measured" -> ProjectListManager.State.MEASURED
            "Done" -> ProjectListManager.State.DONE
            else -> ProjectListManager.State.ALL
        }
    }

    private fun applySortChanges(): ProjectListManager.Sort {
        return when (spinner_sort_type.selectedItem) {
            "Number" -> ProjectListManager.Sort.NUMBER
            "Distance" -> ProjectListManager.Sort.DISTANCE
            "Alphabet" -> ProjectListManager.Sort.ALPHABET
            else -> ProjectListManager.Sort.COMPLEXITY
        }
    }

    private fun applyOrderChanges(): ProjectListManager.Order {
        return when (spinner_sort_order.selectedItem) {
            "Increase" -> ProjectListManager.Order.INCREASE
            else -> ProjectListManager.Order.DECREASE
        }
    }
}