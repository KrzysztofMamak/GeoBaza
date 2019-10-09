package com.mamak.geobaza.data.singleton

import com.mamak.geobaza.data.model.Project

object ProjectLab {
    private var projects = mutableListOf<Project>()

    fun setProjects(projects: List<Project>) {
        ProjectLab.projects = projects.toMutableList()
    }

    fun getAllProjects(): MutableList<Project> = projects

    fun getProject(number: Int): Project? {
        return projects.find {
            it.number == number
        }
    }
}