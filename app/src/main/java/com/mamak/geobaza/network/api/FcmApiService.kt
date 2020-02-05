package com.mamak.geobaza.network.api

import com.mamak.geobaza.network.firebase.FirebaseMessage
import com.mamak.geobaza.utils.constans.AppConstans
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmApiService {
    @Headers(
        "content-type: application/json",
        "Authorization: key=${AppConstans.LEGACY_SERVER_KEY}"
    )
    @POST("fcm/send/")
    fun sendNotification(@Body firebaseMessage: FirebaseMessage): Observable<ResponseBody>
}