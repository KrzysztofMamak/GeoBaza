package com.mamak.geobaza.ui.fragment

import android.content.DialogInterface
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
import kotlinx.android.synthetic.main.item_spinner.view.*

class FilterDialogFragment(private val filterDialogInterface: FilterDialogInterface) : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter_project_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setAdapters()
        setOnClicks()
    }

    private fun createArrayAdapter(itemsArray: Int) : ArrayAdapter<CharSequence> {
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
        var areaFilterType: ProjectListManager.AreaFilterType = when (spinner_area.selectedItemPosition) {
            0 -> ProjectListManager.AreaFilterType.CIESZYN
            1 -> ProjectListManager.AreaFilterType.JASTRZEBIE_ZDROJ
            2 -> ProjectListManager.AreaFilterType.PSZCZYNA
            3 -> ProjectListManager.AreaFilterType.RYBNIK
            4 -> ProjectListManager.AreaFilterType.WODZISLAW
            5 -> ProjectListManager.AreaFilterType.ZORY
            else -> ProjectListManager.AreaFilterType.ALL
        }

        var stateFilterType: ProjectListManager.StateFilterType = when (spinner_state.selectedItemPosition) {
            0 -> ProjectListManager.StateFilterType.MARKED
            1 -> ProjectListManager.StateFilterType.MEASURED
            2 -> ProjectListManager.StateFilterType.DONE
            else -> ProjectListManager.StateFilterType.ALL
        }

        var sortType: ProjectListManager.SortType = when (spinner_sort_type.selectedItemPosition) {
            0 -> ProjectListManager.SortType.NUMBER
            1 -> ProjectListManager.SortType.DISTANCE
            2 -> ProjectListManager.SortType.ALPHABET
            else -> ProjectListManager.SortType.COMPLEXITY
        }

        var orderType: ProjectListManager.OrderType = when (spinner_sort_order.selectedItemPosition) {
            0 -> ProjectListManager.OrderType.INCREASE
            else -> ProjectListManager.OrderType.DECREASE
        }

        filterDialogInterface.filterProjects(areaFilterType, stateFilterType, sortType, orderType)
    }
}