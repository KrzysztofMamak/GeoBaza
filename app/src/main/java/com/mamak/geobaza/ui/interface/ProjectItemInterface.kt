package com.mamak.geobaza.ui.`interface`

import com.mamak.geobaza.data.model.Project

interface ProjectItemInterface {
    fun navigate(x: Double, y: Double)
    fun showMap(projectNumber: Int)
    fun showDetails(projectNumber: Int)
}