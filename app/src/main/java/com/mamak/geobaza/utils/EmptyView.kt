package com.mamak.geobaza.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.mamak.geobaza.R
import kotlinx.android.synthetic.main.layout_empty_view.view.*

class EmptyView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, null, true)
        addView(view)
    }

    fun draw(type: Type) {
        when (type) {
            Type.NO_DATA -> {
                draw(context.getString(R.string.ev_no_data), context.getDrawable(R.drawable.ic_no_data))
            }
            Type.NO_INTERNET -> {
                draw(context.getString(R.string.ev_no_internet), context.getDrawable(R.drawable.ic_no_internet))
            }
            Type.NO_SERVICE -> {
                draw(context.getString(R.string.ev_no_service), context.getDrawable(R.drawable.ic_no_service))
            }
        }
    }

    fun draw(message: String, drawable: Drawable?) {
        iv_empty_view.setImageDrawable(drawable)
        tv_empty_view.text = message
    }

    enum class Type {
        NO_DATA, NO_INTERNET, NO_SERVICE
    }
}