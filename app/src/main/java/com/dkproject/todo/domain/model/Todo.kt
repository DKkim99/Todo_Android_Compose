package com.dkproject.todoapp.domain.model

data class Todo(
    val id:Int=0,
    val category:String?=null,
    val date:Long,
    val title:String,
    val hour: Int? = null,
    val minute: Int? = null,
    val completed:Boolean = false
)