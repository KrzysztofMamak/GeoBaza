package com.mamak.geobaza.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.repository.ProjectLocalRepo
import com.mamak.geobaza.network.api.ProjectApiService
import com.mamak.geobaza.network.connection.GeoBazaResponse
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProjectDetailsActivityViewModel @Inject constructor(
    appDatabase: AppDatabase
) : BaseViewModel() {
    private val projectLocalRepo = ProjectLocalRepo(appDatabase.projectDao())
    private val projectLiveData = MutableLiveData<Project>()

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

    fun getProjectLiveData() = projectLiveData

    override fun onCleared() {
        onStop()
    }
}