package com.example.todo

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatter  {
    fun Date.getDateOnly() : String{
        val formatter = SimpleDateFormat("dd / MM / yyyy" , Locale.US)
        return formatter.format(this)
    }

    fun Date.getTimeOnly() : String{
        val formatter = SimpleDateFormat("hh:mm" , Locale.US)
        return formatter.format(this)
    }
}