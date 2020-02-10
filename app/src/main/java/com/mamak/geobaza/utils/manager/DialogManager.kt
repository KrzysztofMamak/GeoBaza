package com.mamak.geobaza.utils.manager

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object DialogManager {
    fun showYesNoDialog(
        context: Context,
        description: String,
        textYes: String,
        textNo: String,
        yesOnClick: () -> Unit,
        noOnClick: (() -> Unit)? = null
    ) {
        //TODO yes no interfaces
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which){
                DialogInterface.BUTTON_POSITIVE -> {
                    dialog.dismiss()
                    yesOnClick
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    dialog.dismiss()
                    noOnClick
                }
            }
        }

        val builder = AlertDialog.Builder(context)
        builder.setMessage(description)
            .setPositiveButton(textYes, dialogClickListener)
            .setNegativeButton(textNo, dialogClickListener)
            .show()
    }
}