package com.mamak.geobaza.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.Spinner

class ExtendedSpinner(context: Context, attributeSet: AttributeSet)
        : Spinner(context, attributeSet) {
    fun getIndex(phrase: String): Int{
        for (index in 0 until this.count){
            if (this.getItemAtPosition(index) == phrase) {
                return index
            }
        }
        return -1
    }
}