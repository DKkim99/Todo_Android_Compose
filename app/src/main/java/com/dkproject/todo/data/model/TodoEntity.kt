package com.dkproject.todoapp.data.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dkproject.todoapp.domain.model.Todo

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val category:String?=null,
    val date:Long,
    val title:String,
    val hour: Int? = null,
    val minute: Int? = null,
    val completed:Boolean = false,
    val categoryColor:Int = 0

){
    fun toDomainModel():Todo{
        return Todo(
            id=id,
            category=category,
            date=date,
            title=title,
            hour=hour,
            minute=minute,
            completed=completed,
            categoryColor = categoryColor
        )
    }
}


fun Todo.toEntity(): TodoEntity{
    return TodoEntity(
        id=id,
        category=category,
        date=date,
        title=title,
        hour=hour,
        minute=minute,
        completed=completed,
        categoryColor = categoryColor
    )
}