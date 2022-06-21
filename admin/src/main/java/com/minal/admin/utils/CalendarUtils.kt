package com.minal.admin.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


object CalendarUtils {
    private val sdfDate by lazy {  SimpleDateFormat("yyyy-MM-dd") }

    /**
     *@return 10-May*/
    fun getMonthName(inputDate: String?) : String{
        if(inputDate.isNullOrBlank()) return  ""
        val date = sdfDate.parse(inputDate)
        //val outFormat = SimpleDateFormat("EEEE, MMMM dd")
        val outFormat = SimpleDateFormat("MMMM, dd, yyyy")
        return outFormat.format(date!!)
    }



    fun formatDate(date: String?, inputFormat: String, outputFormat: String, lowerCaseAmPm: Boolean = false): String {
        if (date.isNullOrEmpty()) return ""
        Log.d("result1",date.toString())

        return try {
            var mDate = SimpleDateFormat(inputFormat, Locale.getDefault()).parse(date)
            Log.d("result2",mDate.toString())

            if (mDate == null)
                ""
            else {
                if (lowerCaseAmPm)
                    SimpleDateFormat(outputFormat, Locale.getDefault()).format(mDate)
                        .replace("AM", "am")
                        .replace("PM", "pm")
                else
                    SimpleDateFormat(outputFormat, Locale.getDefault()).format(mDate)
            }
        } catch (e: java.lang.Exception) {
            ""
        }

    }

    fun timeZone(date: String?):String?{
        val sourceFormat:SimpleDateFormat? = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sourceFormat?.timeZone = TimeZone.getTimeZone("UTC")
        val parsed = sourceFormat?.parse(date) // => Date is in UTC now
        val tz = TimeZone.getDefault()
        val destFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        destFormat.timeZone = tz

        val result = destFormat.format(parsed)
        return result

    }
}