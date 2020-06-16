package com.mamak.geobaza.base

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    private var currentTheme: String? = null
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        currentTheme = sharedPref.getString("current_theme", "marshmallow")
        setAppTheme(currentTheme)
        setStatusBar()
    }

    override fun onResume() {
        super.onResume()
        val theme = sharedPref.getString("current_theme", "marshmallow")
        if(currentTheme != theme) {
            recreate()
        }
    }

    protected open fun setStatusBar() {}

    protected open fun setAppTheme(currentTheme: String?) {}
}