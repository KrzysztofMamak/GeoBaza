package com.mamak.geobaza.network.api

import com.mamak.geobaza.data.model.Project
import com.mamak.geobaza.network.connection.GeoBazaResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ProjectApiService {
    @Headers("Content-type: application/json")
    @GET("projects/all/")
    fun getProjects(): Observable<List<Project>>

    @Headers("Content-type: application/json")
    @PUT("projects/update/")
    fun updateProject(@Body project: Project): Observable<GeoBazaResponse>
}