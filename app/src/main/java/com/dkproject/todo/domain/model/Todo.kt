package com.dkproject.todoapp.domain.model

import androidx.compose.ui.graphics.Color

data class Todo(
    val id:Int=0,
    val category:String?=null,
    val date:Long = 0L,
    val title:String ="",
    val hour: Int = 0,
    val minute: Int = 0,
    val completed:Boolean = false,
    val categoryColor : Int = 0
)
