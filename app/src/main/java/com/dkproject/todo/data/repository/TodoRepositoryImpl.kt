package com.dkproject.todoapp.data.Repository

import com.dkproject.todoapp.data.local.LocalTodoDataSource
import com.dkproject.todoapp.data.model.toEntity
import com.dkproject.todoapp.domain.model.Todo
import com.dkproject.todoapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        localTodoDataSource.insertTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todo: Todo) {
        localTodoDataSource.deleteTodo(todo.toEntity())
    }

    override suspend fun updateTodo(todo: Todo) {
        localTodoDataSource.updateTodo(todo.toEntity())
    }

    override suspend fun updateCategoryAndColor(
        categoryName: String,
        newCategoryName: String,
        newColor: Int
    ) {
        return localTodoDataSource.updateTodoCategoryColor(categoryName, newCategoryName, newColor)
    }

    override fun getTodoById(id: Int): Flow<Todo?> {
        return localTodoDataSource.getTodoById(id).map {
            it?.toDomainModel()
        }
    }

    override fun getDateTodo(date: Long): Flow<List<Todo>> {
        return localTodoDataSource.getTodoByDate(date).map { todolist ->
            todolist.map {
                it.toDomainModel()
            }
        }
    }
}