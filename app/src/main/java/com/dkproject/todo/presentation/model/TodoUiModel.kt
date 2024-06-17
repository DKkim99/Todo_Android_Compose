package com.dkproject.todo.presentation.model

import android.os.Parcelable
import com.dkproject.todoapp.domain.model.Todo
import kotlinx.parcelize.Parcelize


@Parcelize
data class TodoUiModel(
    val id:Int=0,
    var category:String?=null,
    var date:Long = 0L,
    var title:String ="",
    var hour: Int = 0,
    var minute: Int = 0,
    var completed:Boolean = false,
    var categoryColor : Int = 0
):Parcelable


fun TodoUiModel.toDomainModel() : Todo{
    return Todo(
        id = id,
        category = category,
        date = date,
        title = title,
        hour = hour,
        minute = minute,
        completed = completed,
        categoryColor = categoryColor
    )
}


fun Todo.toUiModel() : TodoUiModel{
    return TodoUiModel(
        id = id,
        category = category,
        date = date,
        title = title,
        hour = hour,
        minute = minute,
        completed = completed,
        categoryColor = categoryColor
    )
}