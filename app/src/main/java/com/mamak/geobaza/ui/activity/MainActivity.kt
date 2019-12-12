package com.mamak.geobaza.ui.activity

import android.os.Bundle
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.base.BaseThemeActivityNoActionBar
import dagger.android.AndroidInjection

class MainActivity : BaseThemeActivityNoActionBar() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

    }
}