package com.mamak.geobaza.network.firebase

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FirebaseMessageData(
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("message")
    @Expose
    var message: String,
    @SerializedName("key1")
    @Expose
    var key1: String,
    @SerializedName("key2")
    @Expose
    var key2: String
)