package com.mamak.geobaza.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.network.api.ProjectApiService
import com.mamak.geobaza.network.connection.GeoBazaResponse
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProjectOverviewViewModel @Inject constructor(
    private val appDatabase: AppDatabase,
    private val projectApiService: ProjectApiService
) : BaseViewModel() {
    private val projectUpdateLiveData = MutableLiveData<Resource<GeoBazaResponse>>()

    fun updateProject(project: Project) {
        addToDisposable(
            projectApiService.updateProject(project)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        if (it.isSuccessful) {
                            appDatabase.projectDao().update(project.toProjectEntity())
                            projectUpdateLiveData.postValue(Resource.success(it))
                        }
                    },
                    onError = {
                        projectUpdateLiveData.postValue(Resource.error(GeoBazaException(it)))
                    }
                )
        )
    }

    fun getProjectUpdateLiveData() = projectUpdateLiveData

    override fun onCleared() {
        onStop()
    }
}