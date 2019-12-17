package com.mamak.geobaza.utils.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mamak.geobaza.R
import kotlinx.android.synthetic.main.layout_dialog.*

class MultipleChoiceDialogFragment(
    private val title: String,
    private val description: String,
    private val actionFirstText: String,
    private val actionSecondText: String?  = null,
    private val actionThirdText: String?  = null,
    private val multipleChoiceDialogInterface: MultipleChoiceDialogInterface
) : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setTitle()
        setDescription()
        setChoices()
    }

    private fun setTitle() {
        tv_title.text = title
    }

    private fun setDescription() {
        tv_description.text = description
    }

    private fun setChoices() {
        setFirstChoice()
        if (actionSecondText != null) {
            setSecondChoice()
            if (actionThirdText != null) {
                setThirdChoice()
            }
        }
    }

    private fun setFirstChoice() {
        tv_action_first.apply {
            text = actionFirstText
            setOnClickListener {
                multipleChoiceDialogInterface.actionSecond()
            }
            visibility = View.VISIBLE
        }
        separator_top.visibility = View.VISIBLE
    }

    private fun setSecondChoice() {
        tv_action_second.apply {
            text = actionSecondText
            setOnClickListener {
                multipleChoiceDialogInterface.actionFirst()
            }
            visibility = View.VISIBLE
        }
        separator_mid.visibility = View.VISIBLE
    }

    private fun setThirdChoice() {
        tv_action_third.apply {
            text = actionThirdText
            setOnClickListener {
                multipleChoiceDialogInterface.actionThird()
            }
            visibility = View.VISIBLE
        }
        separator_bottom.visibility = View.VISIBLE
    }

    interface MultipleChoiceDialogInterface : Parcelable {
        fun actionFirst()
        fun actionSecond()
        fun actionThird()
    }
}