package com.dkproject.todoapp.data.local

import com.dkproject.todoapp.data.model.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class LocalTodoDataSource @Inject constructor(
    private val todoDao: TodoDao
){
    suspend fun insertTodo(todo: TodoEntity) {
        todoDao.insert(todo)
    }

    suspend fun deleteTodo(todo: TodoEntity) {
        todoDao.delete(todo)
    }

    suspend fun updateTodo(todo: TodoEntity){
        todoDao.update(todo)
    }

    fun getTodoById(id: Int): Flow<TodoEntity?> {
        return todoDao.getTodoById(id)
    }


    fun getTodoByDate(date:Long) : Flow<List<TodoEntity>> {
        return todoDao.dateTodo(date)
    }

    suspend fun updateTodoCategoryColor(categoryName: String, newCategoryName:String, newColor: Int) {
        return todoDao.updateCategoryAndColor(categoryName = categoryName, newCategoryName = newCategoryName, newColor = newColor)
    }
}