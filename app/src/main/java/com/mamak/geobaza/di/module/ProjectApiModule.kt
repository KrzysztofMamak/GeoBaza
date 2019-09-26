package com.mamak.geobaza.di.module

import com.mamak.geobaza.network.api.ProjectApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [
    ApiModule::class
])
class ProjectApiModule {
    @Provides
    @Singleton
    fun projectApiService(retrofit: Retrofit): ProjectApiService {
        return retrofit.create(ProjectApiService::class.java)
    }
}