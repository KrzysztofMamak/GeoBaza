package com.mamak.geobaza.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.mamak.geobaza.network.FirebaseAuthenticationApi
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class RegistrationLoginSharedViewModel @Inject constructor() : BaseViewModel() {
    private val loginLiveData = MutableLiveData<Resource<Task<AuthResult>>>()
    private val firebaseAuthenticationApi = FirebaseAuthenticationApi()

    fun login(email: String, password: String) {
        addToDisposable(
            firebaseAuthenticationApi.loginViaEmailAndPassword(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loginLiveData.postValue(Resource.loading())
                }
                .subscribeBy(
                    onNext = {
                        loginLiveData.postValue(Resource.success(it))
                    },
                    onError = {
                        loginLiveData.postValue(Resource.error(it as Exception))
                    }
                )
        )
    }

    fun getLoginLiveData() = loginLiveData
}