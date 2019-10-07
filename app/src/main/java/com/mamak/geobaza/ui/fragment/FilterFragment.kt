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
            when (ProjectListManager.areaFilterType) {
                ProjectListManager.AreaFilterType.CIESZYN -> 0
                ProjectListManager.AreaFilterType.JASTRZEBIE_ZDROJ -> 1
                ProjectListManager.AreaFilterType.PSZCZYNA -> 2
                ProjectListManager.AreaFilterType.RYBNIK -> 3
                ProjectListManager.AreaFilterType.WODZISLAW -> 4
                ProjectListManager.AreaFilterType.ZORY -> 5
                ProjectListManager.AreaFilterType.ALL -> 6
            }
        )
    }

    private fun setStateSpinner() {
        spinner_state.setSelection(
            when (ProjectListManager.stateFilterType) {
                ProjectListManager.StateFilterType.MARKED -> 0
                ProjectListManager.StateFilterType.MEASURED -> 1
                ProjectListManager.StateFilterType.DONE -> 2
                ProjectListManager.StateFilterType.ALL -> 3
            }
        )
    }

    private fun setSortTypeSpinner() {
        spinner_sort_type.setSelection(
            when (ProjectListManager.sortType) {
                ProjectListManager.SortType.NUMBER -> 0
                ProjectListManager.SortType.DISTANCE -> 1
                ProjectListManager.SortType.ALPHABET -> 2
                ProjectListManager.SortType.COMPLEXITY -> 3
            }
        )
    }

    private fun setSortOrderSpinner() {
        spinner_sort_order.setSelection(
            when (ProjectListManager.orderType) {
                ProjectListManager.OrderType.INCREASE -> 0
                ProjectListManager.OrderType.DECREASE -> 1
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
        val areaFilterType = applyAreaFilterChanges()
        val stateFilterType = applyStateFilterChanges()
        val sortType = applySortTypeChanges()
        val orderType = applyOrderTypeChanges()

        ProjectListManager.setAttributes(areaFilterType, stateFilterType, sortType, orderType)
        filterDialogInterface.filterProjects()
    }

    private fun applyAreaFilterChanges(): ProjectListManager.AreaFilterType {
        return when (spinner_area.selectedItemPosition) {
            0 -> ProjectListManager.AreaFilterType.CIESZYN
            1 -> ProjectListManager.AreaFilterType.JASTRZEBIE_ZDROJ
            2 -> ProjectListManager.AreaFilterType.PSZCZYNA
            3 -> ProjectListManager.AreaFilterType.RYBNIK
            4 -> ProjectListManager.AreaFilterType.WODZISLAW
            5 -> ProjectListManager.AreaFilterType.ZORY
            else -> ProjectListManager.AreaFilterType.ALL
        }
    }

    private fun applyStateFilterChanges(): ProjectListManager.StateFilterType {
        return when (spinner_state.selectedItemPosition) {
            0 -> ProjectListManager.StateFilterType.MARKED
            1 -> ProjectListManager.StateFilterType.MEASURED
            2 -> ProjectListManager.StateFilterType.DONE
            else -> ProjectListManager.StateFilterType.ALL
        }
    }

    private fun applySortTypeChanges(): ProjectListManager.SortType {
        return when (spinner_sort_type.selectedItemPosition) {
            0 -> ProjectListManager.SortType.NUMBER
            1 -> ProjectListManager.SortType.DISTANCE
            2 -> ProjectListManager.SortType.ALPHABET
            else -> ProjectListManager.SortType.COMPLEXITY
        }
    }

    private fun applyOrderTypeChanges(): ProjectListManager.OrderType {
        return when (spinner_sort_order.selectedItemPosition) {
            0 -> ProjectListManager.OrderType.INCREASE
            else -> ProjectListManager.OrderType.DECREASE
        }
    }
}