package com.dkproject.todoapp.presentation.util

import java.text.SimpleDateFormat
import java.util.Locale

fun dateFormatter(date:Long) :String{
    return SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(date)
}