package com.mamak.geobaza.ui.viewmodel

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.data.singleton.ProjectLab
import com.mamak.geobaza.network.api.ProjectApiService
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class ProjectListViewModel @Inject constructor(
        private val projectApiService: ProjectApiService,
        //private val appDatabase: AppDatabase,
        private val locationManager: LocationManager
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
                onNext = { projectsLiveData.postValue(Resource.success(it)) },
                onError = { projectsLiveData.postValue(Resource.error(it as Exception)) }
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

    fun getProjectsLiveData() : MutableLiveData<Resource<List<Project>>> {
        return projectsLiveData
    }
}