package com.mamak.geobaza.base

import com.mamak.geobaza.R
import com.mamak.geobaza.base.BaseActivity
import com.mamak.geobaza.utils.constans.AppConstans

open class BaseThemeActivityNoActionBar : BaseActivity() {
    override fun setAppTheme(currentTheme: String?) {
        if (currentTheme == null) {
            setTheme(R.style.Theme_App_MarshMallowNoActionBar)
        } else {
            when (currentTheme) {
                AppConstans.THEME_MARSHMALLOW -> setTheme(R.style.Theme_App_MarshMallowNoActionBar)
                AppConstans.THEME_ALADDIN -> setTheme(R.style.Theme_App_AladdinNoActionBar)
                AppConstans.THEME_DEEP_PURPLE -> setTheme(R.style.Theme_App_DeepPurpleNoActionBar)
                AppConstans.THEME_TINKY_PINKY -> setTheme(R.style.Theme_App_TinkyPinkyNoActionBar)
                AppConstans.THEME_ORANGE_IS_THE_NEW_BLACK -> setTheme(R.style.Theme_App_OrangeIsTheNewBlackNoActionBar)
                AppConstans.THEME_ALICE_IN_WONDERLAND -> setTheme(R.style.Theme_App_AliceInWonderlandNoActionBar)
                AppConstans.THEME_BLACK_AND_YELLOW -> setTheme(R.style.Theme_App_BlackAndYellowNoActionBar)
                AppConstans.THEME_MERRY_CHRISTMAS -> setTheme(R.style.Theme_App_MerryChristmasNoActionBar)
                else -> setTheme(R.style.Theme_App_AliceInWonderlandNoActionBar)
            }
        }
    }
}