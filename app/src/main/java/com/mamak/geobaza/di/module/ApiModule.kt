package com.mamak.geobaza.di.module

import com.mamak.geobaza.network.api.FcmApiService
import com.mamak.geobaza.network.api.ProjectApiService
import com.mamak.geobaza.utils.constans.AppConstans
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [
    RetrofitModule::class
])
class ApiModule {
    @Provides
    @Singleton
    fun projectApiService(retrofitBuilder: Retrofit.Builder): ProjectApiService {
        return retrofitBuilder.baseUrl(AppConstans.BASE_URL_PROJECTS).build()
                .create(ProjectApiService::class.java)
    }

    @Provides
    @Singleton
    fun fcmApiService(retrofitBuider: Retrofit.Builder): FcmApiService {
        return retrofitBuider.baseUrl(AppConstans.BASE_URL_FCM).build()
                .create(FcmApiService::class.java)
    }
}