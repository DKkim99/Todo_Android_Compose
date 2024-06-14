package com.dkproject.todoapp.domain.repository

import com.dkproject.todoapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    fun getTodoById(id: Int): Flow<Todo?>

    fun getDateTodo(date:Long): Flow<List<Todo>>
}