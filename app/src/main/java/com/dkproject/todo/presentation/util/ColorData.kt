package com.dkproject.todo.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

object ColorData {
    val skyBlue = Color(0xFF2E9AFE).toArgb()
    val lightPurple = Color(0xFFA9A9F5).toArgb()
    val megenta = Color(0xFFF78181).toArgb()
    val orrange = Color(0xFFFF8000).toArgb()
    val yellow = Color(0xFFFFFF00).toArgb()

    fun getColors():List<Int>{
        return listOf(
            skyBlue,
            lightPurple,
            megenta,
            orrange,
            yellow
        )
    }
}