package com.example.todo

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

fun CalendarDay.timeInMillis():Long{
    val calender: Calendar = Calendar.getInstance()
    calender.set(this.year , this.month-1 , this.day)
    calender.clearTime()
    return  calender.timeInMillis
}

fun Calendar.clearTime(){
    this.clear(Calendar.HOUR)
    this.clear(Calendar.SECOND)
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.MILLISECOND)
}