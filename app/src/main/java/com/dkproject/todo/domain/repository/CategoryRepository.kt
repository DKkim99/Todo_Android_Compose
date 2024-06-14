package com.dkproject.todoapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun setCategory(categoryData:List<String>)

    fun getCategory():Flow<List<String>>
}