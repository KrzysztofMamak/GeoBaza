package com.mamak.geobaza.utils.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.mamak.geobaza.utils.constans.AppConstans.DATE_DAY
import com.mamak.geobaza.utils.constans.AppConstans.DATE_MONTH
import com.mamak.geobaza.utils.constans.AppConstans.DATE_YEAR
import java.util.*

class DatePickerFragment(
    private val datePickerInterface: DatePickerDialog.OnDateSetListener
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        arguments?.let {
            year = it.getInt(DATE_YEAR)
            month = it.getInt(DATE_MONTH)
            day = it.getInt(DATE_DAY)
        }

        return DatePickerDialog(context, datePickerInterface, year, month, day)
    }
}