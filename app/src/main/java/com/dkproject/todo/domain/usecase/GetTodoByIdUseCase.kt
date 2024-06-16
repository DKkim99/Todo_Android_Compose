package com.dkproject.todo.domain.usecase

import com.dkproject.todoapp.domain.model.Todo
import com.dkproject.todoapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodoByIdUseCase (private val todoRepository: TodoRepository){
    operator fun invoke(id:Int) : Flow<Todo?>{
        return todoRepository.getTodoById(id = id)
    }

}