package com.mamak.geobaza.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.network.api.ProjectApiService
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class ProjectListViewModel @Inject constructor(
        private val projectApiService: ProjectApiService
) : BaseViewModel() {
    private val projectsLiveData = MutableLiveData<Resource<List<Project>>>()

    @SuppressLint("CheckResult")
    fun fetchProjects() {
        projectApiService.getProjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                projectsLiveData.postValue(Resource.loading())
            }
            .subscribeBy (
                onNext = {
                    projectsLiveData.postValue(Resource.success(it))
                },
                onError = {
                    projectsLiveData.postValue(Resource.error(it as Exception))
                }
            )
    }

    fun getProjectsLiveData() : MutableLiveData<Resource<List<Project>>> {
        return projectsLiveData
    }
}