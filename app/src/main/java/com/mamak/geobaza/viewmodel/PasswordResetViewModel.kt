package com.mamak.geobaza.viewmodel

import androidx.lifecycle.MutableLiveData
import com.mamak.geobaza.network.connection.Resource
import com.mamak.geobaza.network.firebase.FirebaseAuthenticationApi
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PasswordResetViewModel @Inject constructor() : BaseViewModel() {
    private val firebaseAuthenticationApi = FirebaseAuthenticationApi()
    private val resetPasswordLiveData = MutableLiveData<Resource<Void>>()

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

    fun getResetPasswordLiveData() = resetPasswordLiveData

    override fun onCleared() {
        onStop()
    }
}