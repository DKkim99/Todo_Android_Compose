package com.dkproject.todoapp.domain.usecase

import com.dkproject.todoapp.domain.repository.CategoryRepository

class SetCategoryUseCase (private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(categoryData: List<String>) {
        categoryRepository.setCategory(categoryData)
    }

}