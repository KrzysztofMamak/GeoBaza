package com.mamak.geobaza.utils.manager

import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.model.ProjectState

object ProjectListManager {
    const val ALL_AREAS = "ALL"

    var area = ALL_AREAS
    var state: State = ProjectListManager.State.ALL
    var sort: Sort = ProjectListManager.Sort.NUMBER
    var order: Order = ProjectListManager.Order.INCREASE

    fun getRequiredProjects(projects: MutableList<Project>): MutableList<Project> {
        var resultList = mutableListOf<Project>()
        resultList.addAll(filterProjects(projects))
        resultList = sortProjects(resultList).toMutableList()

        return resultList
    }

    private fun filterProjects(list: List<Project>): List<Project> {
        var tempList =  mutableListOf<Project>()
        tempList.addAll(list)

        if (area != ALL_AREAS) {
            tempList = tempList.filter {
                it.area == area
            }.toMutableList()
        }

        return tempList.filter {
            when (state) {
                ProjectListManager.State.PROCESSED -> it.state == ProjectState.PROCESSED
                ProjectListManager.State.MARKED -> it.state == ProjectState.MARKED
                ProjectListManager.State.MEASURED -> it.state == ProjectState.MEASURED
                ProjectListManager.State.DONE -> it.state == ProjectState.FINISHED
                else -> true
            }
        }
    }

//    TODO Refactor
    private fun sortProjects(list: List<Project>): List<Project> {
        return when (sort) {
            ProjectListManager.Sort.NUMBER -> {
                if (order == ProjectListManager.Order.INCREASE) {
                    list.sortedBy {
                        it.number
                    }
                } else {
                    list.sortedByDescending {
                        it.number
                    }
                }
            }

            ProjectListManager.Sort.DISTANCE -> {
                if (order == ProjectListManager.Order.INCREASE) {
                    list.sortedBy {
                        LocationManager.calculateDistance(it.pointList[0])
                    }
                } else {
                    list.sortedByDescending {
                        LocationManager.calculateDistance(it.pointList[0])
                    }
                }
            }
            ProjectListManager.Sort.ALPHABET -> {
                if (order == ProjectListManager.Order.INCREASE) {
                    list.sortedBy {
                        it.town
                    }
                } else {
                    list.sortedByDescending {
                        it.town
                    }
                }
            }
            ProjectListManager.Sort.COMPLEXITY -> {
                if (order == ProjectListManager.Order.INCREASE) {
                    list.sortedBy {
                        it.pointList.size
                    }
                } else {
                    list.sortedByDescending {
                        it.pointList.size
                    }
                }
            }
        }
    }

    fun setAttributes(area: String, state: State, sort: Sort, order: Order) {
        ProjectListManager.area = area
        ProjectListManager.state = state
        ProjectListManager.sort = sort
        ProjectListManager.order = order
    }

    enum class State {
        PROCESSED, MARKED, MEASURED, DONE, ALL
    }

    enum class Sort {
        NUMBER, DISTANCE, ALPHABET, COMPLEXITY
    }

    enum class Order {
        INCREASE, DECREASE
    }
}