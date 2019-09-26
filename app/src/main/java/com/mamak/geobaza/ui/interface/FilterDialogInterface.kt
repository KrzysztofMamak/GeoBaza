package com.mamak.geobaza.ui.`interface`

import com.mamak.geobaza.utils.ProjectListManager

interface FilterDialogInterface {
    fun filterProjects(
        areaFilterType: ProjectListManager.AreaFilterType,
        stateFilterType: ProjectListManager.StateFilterType,
        sortType: ProjectListManager.SortType,
        orderType: ProjectListManager.OrderType)
}