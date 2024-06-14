package com.dkproject.todoapp.domain.usecase

import com.dkproject.todoapp.domain.model.Todo
import com.dkproject.todoapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodoByDateUseCase (private val todoRepository: TodoRepository) {
     operator fun invoke(date: Long): Flow<List<Todo>> {
        return todoRepository.getDateTodo(date)
    }

}