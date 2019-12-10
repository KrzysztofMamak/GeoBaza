package com.mamak.geobaza.utils.manager

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import com.mamak.geobaza.R

object ThemeManager {
    fun getColorResByAttr(context: Context, attrColor: Int): Int {
        val outValue = TypedValue()
        context.theme.resolveAttribute(attrColor, outValue, true)
        return outValue.resourceId
    }
}