package com.dkproject.todoapp.domain.usecase

import com.dkproject.todoapp.domain.model.Todo
import com.dkproject.todoapp.domain.repository.TodoRepository

class UpdateTodoUseCase (private val todoRepository: TodoRepository) {
    suspend operator fun invoke(todo: Todo) {
        todoRepository.updateTodo(todo)
    }

}