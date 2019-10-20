package com.mamak.geobaza.data.repository

import com.mamak.geobaza.network.api.ProjectApiService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ProjectRepo(
    private val projectApiService: ProjectApiService,
    private val projectLocalRepo: ProjectLocalRepo
) {
    fun getAllUsers(): Observable<Any> {
        return Observable.mergeDelayError(
            projectApiService.getProjects()
                .doOnNext { it1 ->
                    projectLocalRepo.addProjects(
                        it1.map { it2 ->
                            it2.toProjectEntity()
                        }
                    )
                }.subscribeOn(Schedulers.io()),
            projectLocalRepo.getAllProjects().subscribeOn(Schedulers.io()))
    }
}