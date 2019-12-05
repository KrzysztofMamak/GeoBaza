package com.mamak.geobaza.utils.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
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
                draw(context.getString(
                    R.string.ev_no_data),
                    context.getDrawable(R.drawable.ic_no_data)
                )
            }
            Type.NO_INTERNET -> {
                draw(context.getString(
                    R.string.ev_no_internet),
                    context.getDrawable(R.drawable.ic_no_internet)
                )
            }
            Type.NO_SERVICE -> {
                draw(context.getString(
                    R.string.ev_no_service),
                    context.getDrawable(R.drawable.ic_no_service)
                )
            }
            Type.NO_PASSWORD -> {
                draw(context.getString(
                    R.string.ev_no_password),
                    context.getDrawable(R.drawable.ic_lock),
                    context.getString(R.string.forgot_password)
                )
            }
            Type.NO_FEEDBACK -> {
                draw(context.getString(R.string.something_went_wrong),
                    context.getDrawable(R.drawable.ic_error))
            }
        }
    }

    fun draw(message: String, drawable: Drawable?, title: String? = null) {
        iv_empty_view_icon.setImageDrawable(drawable)
        tv_empty_view_message.text = message
        if (title != null) {
            tv_empty_view_title.apply {
                text = title
                visibility = View.VISIBLE
            }
        }
    }

    enum class Type {
        NO_DATA, NO_INTERNET, NO_SERVICE, NO_PASSWORD, NO_FEEDBACK
    }
}