package com.wenitech.cashdaily.commons.Dialog

import android.app.Dialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.app.TimePickerDialog
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment() {
    private var listener: OnTimeSetListener? = null

    fun setListener(listener: OnTimeSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hora = c[Calendar.HOUR_OF_DAY]
        val minuto = c[Calendar.MINUTE]

        // Create a new instance of DatePickerDialog and return it
        return TimePickerDialog(activity, listener, hora, minuto, true)
    }

    companion object {
        fun newInstance(listener: OnTimeSetListener?): TimePickerFragment {
            val fragment = TimePickerFragment()
            fragment.setListener(listener)
            return fragment
        }
    }
}