package com.dkproject.todoapp.domain.usecase

import com.dkproject.todo.domain.model.CategoryInfo
import com.dkproject.todoapp.domain.repository.CategoryRepository

class SetCategoryUseCase (private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(categoryData: List<CategoryInfo>) {
        categoryRepository.setCategory(categoryData)
    }

}