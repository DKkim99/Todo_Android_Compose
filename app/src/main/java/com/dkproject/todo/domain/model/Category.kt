package com.dkproject.todo.domain.model

import java.util.UUID


data class CategoryInfo(
    val uid : String,
    val category:String,
    val color:Int,
)