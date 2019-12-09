package com.mamak.geobaza.ui.base

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import com.mamak.geobaza.R

open class BaseActivity : AppCompatActivity() {
    protected open fun setActionBarColor() {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.colorPrimary)))
    }

    protected open fun setStatusBar() {}
}