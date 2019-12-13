package com.mamak.geobaza.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.network.api.ProjectApiService
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProjectListViewModelNew @Inject constructor(
    private val projectApiService: ProjectApiService,
    private val appDatabase: AppDatabase
) : BaseViewModel() {
    private val projectListLiveData = MutableLiveData<Resource<List<Project>>>()

    fun fetchProjects() {
        addToDisposable(projectApiService.getProjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                projectListLiveData.postValue(Resource.loading())
            }
            .subscribeBy(
                onNext = {
                    appDatabase.projectDao().insert(
                        it.map { it1 ->
                            it1.toProjectEntity()
                        }
                    )
                    projectListLiveData.postValue(Resource.success(it))
                },
                onError = {
                    val projects = appDatabase.projectDao().getAllProjects().map {
                        it.toProject()
                    }
                    projectListLiveData.postValue(Resource.success(projects))
                }
            )
        )
    }

    fun getProjectListLiveData() = projectListLiveData

    override fun onCleared() {
        onStop()
    }
}