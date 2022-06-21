package com.minal.admin.view

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.minal.admin.R
import java.util.*

class TextViewDatePicker(
    private val _context: Context,
    private var setMaxDate: Boolean = false,
    private var setMinDate : Boolean = false,
    private var mDateInvoke : (String) -> Unit
) : DatePickerDialog.OnDateSetListener {
    private var _day: Int = 0
    private var _month: Int = 0
    private var _birthYear: Int = 0

    private val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
    private val dialog by lazy {
        DatePickerDialog(
            _context, R.style.custom_dialog_theme, this,
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
    }

    fun show(){

        if(setMaxDate) setMaxDate(dialog)

        if(setMinDate) dialog.datePicker.minDate = System.currentTimeMillis() - 1000;

        if(dialog.isShowing) {dialog.dismiss()}
        else dialog.show()
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        _birthYear = year
        _month = monthOfYear
        _day = dayOfMonth
        updateDisplay()
    }


    // updates the date in the birth date EditText
    private fun updateDisplay() {
        var monthStr = ""
        var dayStr = ""
        _month += 1
        monthStr = if(_month < 10){
            "0$_month"
        }else _month.toString()

        dayStr = if(_day < 10){
            "0$_day"
        }else _day.toString()

        val selectedDate = StringBuilder()
            // Month is 0 based so add 1
            .append(_birthYear).append("-").append(monthStr).append("-").append(dayStr)
        mDateInvoke.invoke(selectedDate.toString())

    }

    private fun setMaxDate(dialog: DatePickerDialog) {
        dialog.datePicker.maxDate = System.currentTimeMillis()

    }

}