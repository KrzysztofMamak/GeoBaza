package com.mamak.geobaza.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.network.firebase.FirebaseAuthenticationApi
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor() : BaseViewModel() {
    private val firebaseAuthenticationApi = FirebaseAuthenticationApi()
    private val authViaEmailAndPasswordLiveData = MutableLiveData<Resource<AuthResult>>()
    private val authViaGoogleLiveData = MutableLiveData<Resource<AuthResult>>()

    fun authViaEmailAndPassword(email: String, password: String) {
        addToDisposable(
            firebaseAuthenticationApi.authViaEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    authViaEmailAndPasswordLiveData.postValue(Resource.loading())
                }
                .subscribeBy(
                    onNext = {
                        authViaEmailAndPasswordLiveData.postValue(Resource.success(it))
                    },
                    onError = {
                        authViaEmailAndPasswordLiveData.postValue(Resource.error(GeoBazaException(it)))
                    }
                )
        )
    }

    fun authViaGoogle(authCredential: AuthCredential) {
        addToDisposable(
            firebaseAuthenticationApi.authViaGoogle(authCredential)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    authViaGoogleLiveData.postValue(Resource.loading())
                }
                .subscribeBy(
                    onNext = {
                        authViaGoogleLiveData.postValue(Resource.success(it))
                    },
                    onError = {
                        authViaGoogleLiveData.postValue(Resource.error(GeoBazaException(it)))
                    }
                )
        )
    }

    fun getAuthViaEmailAndPasswordLiveData() = authViaEmailAndPasswordLiveData
    fun getAuthViaGoogleLiveData() = authViaGoogleLiveData

    override fun onCleared() {
        onStop()
    }
}