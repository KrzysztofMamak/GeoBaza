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
        dialogOnClickListener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(description)
            .setPositiveButton(textYes, dialogOnClickListener)
            .setNegativeButton(textNo, dialogOnClickListener)
            .show()
    }
}