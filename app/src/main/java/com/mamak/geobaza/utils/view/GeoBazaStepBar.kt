package com.mamak.geobaza.utils.view

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import com.mamak.geobaza.R
import com.mamak.geobaza.utils.manager.AttributesManager
import kotlinx.android.synthetic.main.layout_step_bar.view.*

class GeoBazaStepBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.layout_step_bar, this)
    }

    fun setState(isProcessed: Boolean, isMarked: Boolean, isMeasured: Boolean, isFinished: Boolean) {
        setStep(iv_first_step_foreground, iv_first_step_background, isProcessed)
        setStep(iv_second_step_foreground, iv_second_step_background, isMarked)
        setStep(iv_third_step_foreground, iv_third_step_background, isMeasured)
        setStep(iv_fourth_step_foreground, iv_fourth_step_background, isFinished)
    }

    private fun setStep(foreground: ImageView, background: ImageView, isChecked: Boolean) {
//        TODO COLORRR
        val foregroundColor = R.color.white
//        val foregroundColor =
//            if (isChecked) {
//                R.color.white
//            } else {
//                AttributesManager.getColorResByAttr(context.theme, R.attr.colorSecondaryLight)
//            }
        val drawable = if (isChecked) R.drawable.item_circle_full else R.drawable.item_circle
        context?.let {
            ImageViewCompat.setImageTintList(
                foreground,
                ColorStateList.valueOf(it.getColor(foregroundColor)))
        }
        background.setImageDrawable(context?.getDrawable(drawable))
    }

    enum class State {
        RECEIVED, PROCESSED, MARKED, MEASURED, FINISHED
    }
}