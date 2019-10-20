package com.mamak.geobaza.data.repository

import android.annotation.SuppressLint
import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.network.api.ProjectApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val projectApiService: ProjectApiService
) {
    @SuppressLint("CheckResult")
    fun getProjects() {
        projectApiService.getProjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = {
                    insertToDatabase(it)
                },
                onError = {
                    loadFromDatabase()
                }
            )
    }

    private fun insertToDatabase(projects: List<Project>) {
        appDatabase.projectDao().insert(
            projects.map {
                it.toProjectEntity()
            }
        )
    }

    private fun loadFromDatabase(): List<Project> {
        return appDatabase.projectDao().getAllProjects().map {
            it.toProject()
        }
    }
}