package com.mamak.geobaza.network.firebase

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FirebaseMessage(
    @SerializedName("to")
    @Expose
    var receiver: String,
    @SerializedName("data")
    @Expose
    var data: FirebaseMessageData
)