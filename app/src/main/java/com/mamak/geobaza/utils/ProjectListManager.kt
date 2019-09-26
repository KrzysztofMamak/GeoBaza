package com.mamak.geobaza.utils

import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.singleton.ProjectLab

object ProjectListManager {
    private var areaFilterType: AreaFilterType = ProjectListManager.AreaFilterType.ALL
    private var stateFilterType: StateFilterType = ProjectListManager.StateFilterType.ALL
    private var sortType: SortType = ProjectListManager.SortType.NUMBER
    private var orderType: OrderType = ProjectListManager.OrderType.INCREASE

    fun getRequiredProjects(): MutableList<Project> {
        val originalList = ProjectLab.getAllProjects()
        var resultList = mutableListOf<Project>()

        resultList.addAll(filterProjects(originalList, areaFilterType, stateFilterType))
        resultList = sortProjects(resultList, sortType, orderType).toMutableList()

        return resultList
    }

    private fun filterProjects(list: List<Project>, areaFilterType: AreaFilterType, stateFilterType: StateFilterType): List<Project> {
        var tempList =  mutableListOf<Project>()
        tempList.addAll(list)
        tempList = when (areaFilterType) {
            ProjectListManager.AreaFilterType.ZORY -> {
                tempList.filter {
                    it.area == "Zory"
                }
            }
            ProjectListManager.AreaFilterType.WODZISLAW -> {
                tempList.filter {
                    it.area == "Wodzislaw"
                }
            }
            ProjectListManager.AreaFilterType.PSZCZYNA -> {
                tempList.filter {
                    it.area == "Pszczyna"
                }
            }
            ProjectListManager.AreaFilterType.RYBNIK -> {
                tempList.filter {
                    it.area == "Rybnik"
                }
            }
            ProjectListManager.AreaFilterType.CIESZYN -> {
                tempList.filter {
                    it.area == "Cieszyn"
                }
            }
            ProjectListManager.AreaFilterType.JASTRZEBIE_ZDROJ -> {
                tempList.filter {
                    it.area == "Jastrzebie-Zdroj"
                }
            }
            else -> list
        }.toMutableList()

        return when (stateFilterType) {
            ProjectListManager.StateFilterType.MARKED -> {
                tempList.filter {
                    it.isMarked
                }
            }
            ProjectListManager.StateFilterType.MEASURED -> {
                tempList.filter {
                    it.isMeasured
                }
            }
            ProjectListManager.StateFilterType.DONE -> {
                tempList.filter {
                    it.isDone
                }
            }
            else -> tempList
        }
    }

    private fun sortProjects(list: List<Project>, sortType: SortType, orderType: OrderType): List<Project> {
        return when (sortType) {
            ProjectListManager.SortType.NUMBER -> {
                if (orderType == ProjectListManager.OrderType.INCREASE) {
                    list.sortedBy {
                        it.number
                    }
                } else {
                    list.sortedByDescending {
                        it.number
                    }
                }
            }
            ProjectListManager.SortType.DISTANCE -> {
                if (orderType == ProjectListManager.OrderType.INCREASE) {
                    list.sortedBy {
                        it.distance
                    }
                } else {
                    list.sortedByDescending {
                        it.distance
                    }
                }
            }
            ProjectListManager.SortType.ALPHABET -> {
                if (orderType == ProjectListManager.OrderType.INCREASE) {
                    list.sortedBy {
                        it.area
                    }
                } else {
                    list.sortedByDescending {
                        it.area
                    }
                }
            }
            ProjectListManager.SortType.COMPLEXITY -> {
                if (orderType == ProjectListManager.OrderType.INCREASE) {
                    list.sortedBy {
                        it.pointList?.size
                    }
                } else {
                    list.sortedByDescending {
                        it.pointList?.size
                    }
                }
            }
        }
    }

    fun setAttributes(
        areaFilterType: AreaFilterType,
        stateFilterType: StateFilterType,
        sortType: SortType,
        orderType: OrderType) {
        this.areaFilterType = areaFilterType
        this.stateFilterType = stateFilterType
        this.sortType = sortType
        this.orderType = orderType
    }

    enum class AreaFilterType {
        ZORY, WODZISLAW, PSZCZYNA, RYBNIK, CIESZYN, JASTRZEBIE_ZDROJ, ALL
    }

    enum class StateFilterType {
        DONE, MEASURED, MARKED, ALL
    }

    enum class SortType {
        NUMBER, DISTANCE, ALPHABET, COMPLEXITY
    }

    enum class OrderType {
        INCREASE, DECREASE
    }
}