package com.mamak.geobaza.di.application

import android.app.Application
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.mamak.geobaza.di.component.DaggerAppComponent
import com.mamak.geobaza.di.module.*
import com.mamak.geobaza.ui.activity.ProjectListActivity
import com.mamak.geobaza.ui.activity.RegistrationLoginActivity

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GeoApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .projectApiModule(ProjectApiModule())
            .interfaceModule(InterfaceModule())
            .picassoModule(PicassoModule())
            .dbModule(DbModule())
            .locationModule(LocationModule())
            .build()
            .inject(this)
        checkUserSection()
    }

    private fun checkUserSection() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, ProjectListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}