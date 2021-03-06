package com.mamak.geobaza.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.mamak.geobaza.R
import com.mamak.geobaza.base.BaseActivity
import com.mamak.geobaza.utils.constans.AppConstans

open class BaseThemeActivityActionBar : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBarColor()
    }

    override fun setAppTheme(currentTheme: String?) {
        if (currentTheme == null) {
            setTheme(R.style.Theme_App_MarshMallowActionBar)
        } else {
            when (currentTheme) {
                AppConstans.THEME_MARSHMALLOW -> setTheme(R.style.Theme_App_MarshMallowActionBar)
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