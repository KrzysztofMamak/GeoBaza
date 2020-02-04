package com.mamak.geobaza.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.repository.ProjectLocalRepo
import com.mamak.geobaza.network.api.FcmApiService
import com.mamak.geobaza.network.api.ProjectApiService
import com.mamak.geobaza.network.connection.GeoBazaResponse
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.network.firebase.FirebaseMessage
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProjectDetailsSharedViewModel @Inject constructor(
    private val appDatabase: AppDatabase,
    private val projectApiService: ProjectApiService,
    private val fcmApiService: FcmApiService
) : BaseViewModel() {
    private val projectLocalRepo = ProjectLocalRepo(appDatabase.projectDao())
    private val projectLiveData = MutableLiveData<Project>()
    private val projectUpdateLiveData = MutableLiveData<Resource<GeoBazaResponse>>()

    fun getProjectFromDb(projectNumber: Int) {
        addToDisposable(projectLocalRepo.getProjectByNumber(projectNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    projectLiveData.postValue(it.toProject())
                }
            )
        )
    }

    fun updateProject(project: Project, firebaseMessage: FirebaseMessage) {
        addToDisposable(
            projectApiService.updateProject(project)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    projectUpdateLiveData.postValue(Resource.loading())
                }
                .subscribeBy(
                    onNext = {
                        if (!it.isSuccessful) {
                            appDatabase.projectDao().update(project.toProjectEntity())
                            fcmApiService.sendNotification(firebaseMessage)
                            projectUpdateLiveData.postValue(Resource.success(it))
                        }
                    },
                    onError = {
                        projectUpdateLiveData.postValue(Resource.error(GeoBazaException(it)))
                    }
                )
        )
    }

    fun getProjectLiveData() = projectLiveData
    fun getProjectUpdateLiveData() = projectUpdateLiveData

    override fun onCleared() {
        onStop()
    }
}