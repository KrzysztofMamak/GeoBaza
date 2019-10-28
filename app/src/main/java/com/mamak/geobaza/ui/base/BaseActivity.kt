package com.mamak.geobaza.ui.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mamak.geobaza.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActionBarColor()
        setStatusBar()
    }

    protected open fun setActionBarColor() {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.colorPrimary)))
    }

//    TODO Refactor
    protected open fun setStatusBar() {}
}