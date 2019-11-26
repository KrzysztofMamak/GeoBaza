package com.mamak.geobaza.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.repository.ProjectLocalRepo
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProjectDataEditViewModel @Inject constructor(
    appDatabase: AppDatabase
) : BaseViewModel() {
    private val projectLiveData = MutableLiveData<Project>()
    private val projectLocalRepo = ProjectLocalRepo(appDatabase.projectDao())

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