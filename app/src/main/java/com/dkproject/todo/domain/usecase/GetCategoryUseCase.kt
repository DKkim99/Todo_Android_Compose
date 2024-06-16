package com.dkproject.todoapp.domain.usecase

import com.dkproject.todo.domain.model.CategoryInfo
import com.dkproject.todoapp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetCategoryUseCase (private val categoryRepository: CategoryRepository) {
    operator fun invoke() : Flow<List<CategoryInfo>>{
        return categoryRepository.getCategory()
    }
}