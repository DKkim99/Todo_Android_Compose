package com.dkproject.todo.domain.usecase

import com.dkproject.todoapp.domain.repository.TodoRepository

class UpdateTodoCategoryAndColorUseCase (private val todoRepository: TodoRepository) {
    suspend operator fun invoke(categoryName: String, newCategoryName:String, newColor: Int) {
        todoRepository.updateCategoryAndColor(categoryName = categoryName,newCategoryName = newCategoryName, newColor = newColor)
    }

}