package com.mamak.geobaza.network.api

import com.mamak.geobaza.data.model.Project
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface ProjectApiService {
    @Headers("Content-type: application/json")
    @GET("projects/all/")
    fun getProjects(): Observable<List<Project>>

//    @Headers("Content-type: application/json")
//    @GET("images/{number}/")
//    fun getImage(@Path("number") number: Int): Call<ResponseBody>
}