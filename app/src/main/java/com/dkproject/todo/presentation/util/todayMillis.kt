package com.dkproject.todoapp.presentation.util

import java.util.Calendar


fun getTodayDateWithoutTime(): Long {
    val calendar = Calendar.getInstance()
    // 시간 정보를 0으로 설정
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}