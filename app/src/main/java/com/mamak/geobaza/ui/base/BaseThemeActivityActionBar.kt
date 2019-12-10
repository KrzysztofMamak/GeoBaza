package com.mamak.geobaza.ui.base

import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import com.mamak.geobaza.R
import com.mamak.geobaza.utils.constans.AppConstans

open class BaseThemeActivityActionBar : BaseActivity() {
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
            setTheme(R.style.Theme_App_MarshMallowActionBar)
        } else {
            when (currentTheme) {
                //AppConstans.THEME_MARSHMALLOW -> setTheme(R.style.Theme_App_MarshMallowActionBar)
                AppConstans.THEME_ALADDIN -> setTheme(R.style.Theme_App_AladdinActionBar)
                AppConstans.THEME_DEEP_PURPLE -> setTheme(R.style.Theme_App_DeepPurpleActionBar)
                AppConstans.THEME_TINKY_PINKY -> setTheme(R.style.Theme_App_TinkyPinkyActionBar)
                AppConstans.THEME_ORANGE_IS_THE_NEW_BLACK -> setTheme(R.style.Theme_App_OrangeIsTheNewBlackActionBar)
                AppConstans.THEME_ALICE_IN_WONDERLAND -> setTheme(R.style.Theme_App_AliceInWonderlandActionBar)
                AppConstans.THEME_BLACK_AND_YELLOW -> setTheme(R.style.Theme_App_BlackAndYellowActionBar)
                AppConstans.THEME_MERRY_CHRISTMAS -> setTheme(R.style.Theme_App_MerryChristmasActionBar)
                else -> setTheme(R.style.Theme_App_AliceInWonderlandActionBar)
            }
        }
    }

    protected open fun setActionBarColor() {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.colorPrimary)))
    }
}