package com.mamak.geobaza.viewmodel

import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.base.BaseViewModel
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val appDatabase: AppDatabase
) : BaseViewModel() {
    fun getProjectsFromDatabase(): List<Project> {
        return appDatabase.projectDao().getAllProjects().map {
            it.toProject()
        }
    }

    override fun onCleared() {
        onStop()
    }
}