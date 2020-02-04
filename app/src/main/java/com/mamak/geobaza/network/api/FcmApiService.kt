package com.mamak.geobaza.network.api

import com.mamak.geobaza.network.firebase.FirebaseMessage
import com.mamak.geobaza.utils.constans.AppConstans
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FcmApiService {
    @Headers("content-type: application/json", "Authorization: key=${AppConstans.SERVER_KEY}")
    @POST("/fcm/send/{firebaseMessage}")
    fun sendNotification(@Body firebaseMessage: FirebaseMessage)
}