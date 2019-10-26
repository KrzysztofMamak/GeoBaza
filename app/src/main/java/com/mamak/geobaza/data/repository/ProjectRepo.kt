package com.mamak.geobaza.data.repository

import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.network.api.ProjectApiService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ProjectRepo(
    private val projectApiService: ProjectApiService,
    private val projectLocalRepo: ProjectLocalRepo,
    private val appDatabase: AppDatabase
) {
    fun getAllUsers(): Observable<Any> {
        return Observable.mergeDelayError(
            projectApiService.getProjects()
                .doOnNext { it1 ->
                    it1.forEach {
                        appDatabase.projectDao().insert(it.toProjectEntity())
                    }
                }.subscribeOn(Schedulers.io()),
            projectLocalRepo.getAllProjects().subscribeOn(Schedulers.io()))
    }
}