package com.mamak.geobaza.di.module

import android.app.Application
import androidx.room.Room
import com.mamak.geobaza.data.db.AppDatabase
import com.mamak.geobaza.data.db.dao.ProjectDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    ContextModule::class
])
class DbModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "geo_info.db")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideProjectDao(appDatabase: AppDatabase): ProjectDao {
        return appDatabase.projectDao()
    }
}