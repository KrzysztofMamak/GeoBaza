package com.mamak.geobaza.ui.base

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import com.mamak.geobaza.R
import com.mamak.geobaza.utils.constans.AppConstans

open class BaseThemeActivityNoActionBar : BaseActivity() {
    private var currentTheme: String? = null
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        currentTheme = sharedPref.getString("current_theme", "marshmallow")
        setAppTheme(currentTheme)
        setStatusBar()
        setActionBarColor()
    }

    override fun onResume() {
        super.onResume()
        val theme = sharedPref.getString("current_theme", "marshmallow")
        if(currentTheme != theme) {
            recreate()
        }
    }

    private fun setAppTheme(currentTheme: String?) {
        if (currentTheme == null) {
            setTheme(R.style.Theme_App_MarshMallowNoActionBar)
        } else {
            when (currentTheme) {
                AppConstans.THEME_ALADDIN -> setTheme(R.style.Theme_App_AladdinNoActionBar)
                AppConstans.THEME_DEEP_PURPLE -> setTheme(R.style.Theme_App_DeepPurpleNoActionBar)
                AppConstans.THEME_TINKY_PINKY -> setTheme(R.style.Theme_App_TinkyPinkyNoActionBar)
                AppConstans.THEME_ORANGE_IS_THE_NEW_BLACK -> setTheme(R.style.Theme_App_OrangeIsTheNewBlackNoActionBar)
                AppConstans.THEME_ALICE_IN_WONDERLAND -> setTheme(R.style.Theme_App_AliceInWonderlandNoActionBar)
                AppConstans.THEME_BLACK_AND_YELLOW -> setTheme(R.style.Theme_App_BlackAndYellowNoActionBar)
                AppConstans.THEME_MERRY_CHRISTMAS -> setTheme(R.style.Theme_App_MerryChristmasNoActionBar)
                else -> setTheme(R.style.Theme_App_TinkyPinkyNoActionBar)
            }
        }
    }
}