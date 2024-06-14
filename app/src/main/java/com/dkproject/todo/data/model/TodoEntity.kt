package com.dkproject.todoapp.data.model

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
    val completed:Boolean = false
){
    fun toDomainModel():Todo{
        return Todo(
            id=id,
            category=category,
            date=date,
            title=title,
            hour=hour,
            minute=minute,
            completed=completed
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
        completed=completed
    )
}