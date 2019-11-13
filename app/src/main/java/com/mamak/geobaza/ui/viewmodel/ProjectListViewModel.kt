package com.mamak.geobaza.ui.viewmodel

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.singleton.ProjectLab
import com.mamak.geobaza.network.FirebaseAuthenticationApi
import com.mamak.geobaza.network.api.ProjectApiService
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProjectListViewModel @Inject constructor(
    private val projectApiService: ProjectApiService,
    private val appDatabase: AppDatabase,
    private val locationManager: LocationManager
) : BaseViewModel() {
    private val projectsLiveData = MutableLiveData<Resource<List<Project>>>()

    @SuppressLint("CheckResult")
    fun fetchProjectsByRepo() {
//        TODO replace with repository methods
        addToDisposable(projectApiService.getProjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                projectsLiveData.postValue(Resource.loading())
            }
            .subscribeBy (
                onNext = {
                    appDatabase.projectDao().insert(
                        it.map { it1 ->
                            it1.toProjectEntity()
                        }
                    )
                    projectsLiveData.postValue(Resource.success(it))
                },
                onError = {
                    val projects = appDatabase.projectDao().getAllProjects().map {
                        it.toProject()
                    }
                    projectsLiveData.postValue(Resource.success(projects))
                }
            )
        )
    }

    @SuppressLint("MissingPermission")
    fun shotLocation() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                ProjectLab.setCurrentLocation(location)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String?) {}
            override fun onProviderDisabled(provider: String?) {}
        })
    }

    fun getProjectsLiveData() = projectsLiveData

    override fun onCleared() {
        onStop()
    }
}