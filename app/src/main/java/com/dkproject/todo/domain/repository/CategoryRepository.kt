package com.dkproject.todoapp.domain.repository

import com.dkproject.todo.domain.model.CategoryInfo
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun setCategory(categoryData:List<CategoryInfo>)

    fun getCategory():Flow<List<CategoryInfo>>
}