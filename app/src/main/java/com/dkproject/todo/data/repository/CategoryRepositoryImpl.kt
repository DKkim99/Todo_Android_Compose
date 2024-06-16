package com.dkproject.todoapp.data.Repository

import com.dkproject.todo.domain.model.CategoryInfo
import com.dkproject.todoapp.data.local.datastore.CategoryDataStore
import com.dkproject.todoapp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataStore: CategoryDataStore
):CategoryRepository {
    override suspend fun setCategory(categoryData: List<CategoryInfo>) {
        categoryDataStore.saveCategoryData(categoryData = categoryData)
    }

    override fun getCategory(): Flow<List<CategoryInfo>> = categoryDataStore.getCategoryData()
}