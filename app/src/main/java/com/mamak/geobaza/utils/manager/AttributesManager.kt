package com.mamak.geobaza.utils.manager

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import com.mamak.geobaza.R

object AttributesManager {
    fun getColorResByAttr(theme: Resources.Theme, @AttrRes attrColor: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorSecondaryDark, typedValue, true)
        return typedValue.data
    }
}