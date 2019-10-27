package com.mamak.geobaza.utils.manager

import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.singleton.ProjectLab

//TODO Refactor, Safe
object ProjectListManager {
    var area = "All"
    var state: State =
        ProjectListManager.State.ALL
    var sort: Sort =
        ProjectListManager.Sort.NUMBER
    var order: Order =
        ProjectListManager.Order.INCREASE

    fun getRequiredProjects(): MutableList<Project> {
        val originalList = ProjectLab.getAllProjects()
        var resultList = mutableListOf<Project>()

        resultList.addAll(filterProjects(originalList))
        resultList = sortProjects(resultList).toMutableList()

        return resultList
    }

    private fun filterProjects(list: List<Project>): List<Project> {
        var tempList =  mutableListOf<Project>()
        tempList.addAll(list)

        if (area != "All") {
            tempList = tempList.filter {
                it.area == area
            }.toMutableList()
        }


        return tempList.filter {
            when (state) {
                ProjectListManager.State.MARKED -> it.isMarked
                ProjectListManager.State.MEASURED -> it.isMeasured
                ProjectListManager.State.DONE -> it.isDone
                else -> true
            }
        }
    }

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
//            TODO Check
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
        MARKED, MEASURED, DONE, ALL
    }

    enum class Sort {
        NUMBER, DISTANCE, ALPHABET, COMPLEXITY
    }

    enum class Order {
        INCREASE, DECREASE
    }
}