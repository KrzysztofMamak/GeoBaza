package com.mamak.geobaza.utils.view

import android.content.Context
import android.util.AttributeSet
import android.widget.Spinner

class ExtendedSpinner(context: Context, attributeSet: AttributeSet)
        : Spinner(context, attributeSet) {
    fun getIndex(phrase: String?): Int{
        for (index in 0 until count){
            if (getItemAtPosition(index) == phrase) {
                return index
            }
        }
        return 0
    }
}