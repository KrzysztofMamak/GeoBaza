package com.mamak.geobaza.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.network.firebase.FirebaseAuthenticationApi
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : BaseViewModel() {
    private val googleSignOutLiveData = MutableLiveData<Resource<Void>>()
    private val firebaseAuthenticationApi = FirebaseAuthenticationApi()

    fun googleSignOut(googleSignInClient: GoogleSignInClient) {
        addToDisposable(
            firebaseAuthenticationApi.googleSignOut(googleSignInClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    googleSignOutLiveData.postValue(Resource.loading())
                }
                .subscribeBy(
                    onComplete = {
                        googleSignOutLiveData.postValue(Resource.success(null))
                    },
                    onError = {
                        googleSignOutLiveData.postValue(Resource.error(it as GeoBazaException))
                    }
                )
        )
    }

    fun getGoogleSignOutLiveData() = googleSignOutLiveData

    override fun onCleared() {
        onStop()
    }
}