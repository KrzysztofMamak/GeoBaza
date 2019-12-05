package com.mamak.geobaza.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.mamak.geobaza.network.firebase.FirebaseAuthenticationApi
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegistrationLoginSharedViewModel @Inject constructor() : BaseViewModel() {
    private val firebaseAuthenticationApi = FirebaseAuthenticationApi()
    private val authViaEmailLiveData = MutableLiveData<Resource<AuthResult>>()
    private val authViaGoogleLiveData = MutableLiveData<Resource<AuthResult>>()
    private val registrationLiveData = MutableLiveData<Resource<AuthResult>>()
    private val resetPasswordLiveData = MutableLiveData<Resource<Void>>()


    fun authViaEmailAndPassword(email: String, password: String) {
        addToDisposable(
            firebaseAuthenticationApi.authViaEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    authViaEmailLiveData.postValue(Resource.loading())
                }
                .subscribeBy(
                    onNext = {
                        authViaEmailLiveData.postValue(Resource.success(it))
                    },
                    onError = {
                        authViaEmailLiveData.postValue(Resource.error(GeoBazaException(it)))
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

    fun registerViaEmailAndPassword(email: String, password: String) {
        addToDisposable(
            firebaseAuthenticationApi.registerViaEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    registrationLiveData.postValue(Resource.loading())
                }
                .subscribeBy(
                    onNext = {
                        registrationLiveData.postValue(Resource.success(it))
                    },
                    onError = {
                        registrationLiveData.postValue(Resource.error(GeoBazaException(it)))
                    }
                )
        )
    }

    fun resetPassword(email: String) {
        addToDisposable(
            firebaseAuthenticationApi.resetPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    resetPasswordLiveData.postValue(Resource.loading())
                }
                .subscribeBy(
                    onComplete = {
                        resetPasswordLiveData.postValue(Resource.success(null))
                    },
                    onError = {
                        resetPasswordLiveData.postValue(Resource.error(GeoBazaException(it)))
                    }
                )
        )
    }

    fun getAuthViaEmailLiveData() = authViaEmailLiveData
    fun getAuthViaGoogleLiveData() = authViaGoogleLiveData
    fun getRegistrationLiveData() = registrationLiveData
    fun getResetPasswordLiveData() = resetPasswordLiveData

    override fun onCleared() {
        onStop()
    }
}