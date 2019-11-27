package com.mamak.geobaza.utils.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import com.mamak.geobaza.R
import kotlinx.android.synthetic.main.layout_geobaza_view.view.*

class GeoBazaLogo(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {init {
        inflate(context, R.layout.layout_geobaza_view, this)
        context.obtainStyledAttributes(attrs, R.styleable.GeoBazaLogo).apply {
            ImageViewCompat.setImageTintList(
                iv_foreground,
                ColorStateList.valueOf(
                    getColor(R.styleable.GeoBazaLogo_foregroundColor, 0)
                )
            )
            ImageViewCompat.setImageTintList(
                iv_background,
                ColorStateList.valueOf(
                    getColor(R.styleable.GeoBazaLogo_foregroundColor, 0)
                )
            )
            recycle()
        }
    }
}