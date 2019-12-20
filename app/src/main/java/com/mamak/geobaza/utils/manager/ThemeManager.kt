package com.mamak.geobaza.utils.manager

import android.content.Context
import android.util.TypedValue

object ThemeManager {
    fun getColorResByAttr(context: Context?, attrColor: Int): Int {
        val outValue = TypedValue()
        context?.theme?.resolveAttribute(attrColor, outValue, true)
        return outValue.resourceId
    }
}