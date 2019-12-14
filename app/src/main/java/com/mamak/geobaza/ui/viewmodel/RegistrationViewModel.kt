package com.mamak.geobaza.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.network.firebase.FirebaseAuthenticationApi
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegistrationViewModel @Inject constructor() : BaseViewModel() {
    private val firebaseAuthenticationApi = FirebaseAuthenticationApi()
    private val registrationLiveData = MutableLiveData<Resource<AuthResult>>()

    fun register(email: String, password: String) {
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

    fun getRegistrationLiveData() = registrationLiveData

    override fun onCleared() {
        onStop()
    }
}