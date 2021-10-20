package com.movies.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class DateUtils{

    companion object {



        fun getStringDate(date: String): String {
            //String date_ = date;
            val dateArray = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            try {
                val mDate = sdf.parse(date)
                val timeInMilliseconds = mDate.time
                val dateString = formatDate(timeInMilliseconds).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                return dateString[0].substring(0,3) + " " + dateString[1] + ", " + dateArray[0]
            } catch (e: ParseException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            return ""
        }





        /**
         * Formats timestamp to 'date month' format (e.g. 'February 3').
         */
        fun formatDate(timeInMillis: Long): String {
            val dateFormat = SimpleDateFormat("MMMM dd", Locale.getDefault())
            return dateFormat.format(timeInMillis)
        }




    }

}