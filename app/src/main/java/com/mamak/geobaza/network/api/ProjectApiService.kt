package com.mamak.geobaza.network.api

import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.network.connection.GeoBazaResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ProjectApiService {
    @Headers("Content-type: application/json")
    @GET("projects/all/")
    fun getProjects(): Observable<List<Project>>

    @Headers("Content-type: application/json")
    @POST("projects/update/{project}/")
    fun updateProject(@Path("project") project: Project): Observable<GeoBazaResponse>

//    @Headers("Content-type: application/json")
//    @GET("images/{number}/")
//    fun getImage(@Path("number") number: Int): Call<ResponseBody>
}