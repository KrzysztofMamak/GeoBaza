package com.mamak.geobaza.ui.viewmodel

import android.location.Location
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.network.api.ProjectApiService
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.ui.base.BaseViewModel
import com.mamak.geobaza.utils.constans.AppConstans.INTERVAL_LOCATION_FASTEST
import com.mamak.geobaza.utils.constans.AppConstans.INTERVAL_LOCATION_NORMAL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProjectListViewModel @Inject constructor(
    private val projectApiService: ProjectApiService,
    private val appDatabase: AppDatabase,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : BaseViewModel() {
    private val projectListLiveData = MutableLiveData<Resource<List<Project>>>()
    private val locationLiveData = MutableLiveData<Resource<Location>>()

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

    fun getLocation() {
        val locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = INTERVAL_LOCATION_NORMAL
        locationRequest.fastestInterval = INTERVAL_LOCATION_FASTEST

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        locationResult?.let {
                            if (it.lastLocation != null) {
                                locationLiveData.postValue(Resource.success(
                                    locationResult.lastLocation)
                                )
                            } else {
                                locationLiveData.postValue(Resource.success(null))
                            }
                        }
                    }
                }, Looper.getMainLooper())
    }

    fun getProjectListLiveData() = projectListLiveData
    fun getLocationLiveData() = locationLiveData

    override fun onCleared() {
        onStop()
    }
}