package com.dkproject.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dkproject.todoapp.data.model.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo:TodoEntity)

    @Update
    suspend fun update(todo:TodoEntity)

    @Delete
    suspend fun delete(todo:TodoEntity)

    @Query("SELECT * FROM todo_table WHERE id=:id")
    fun getTodoById(id:Int):Flow<TodoEntity>

    @Query("SELECT * FROM todo_table WHERE date=:date")
    fun dateTodo(date:Long):Flow<List<TodoEntity>>
}