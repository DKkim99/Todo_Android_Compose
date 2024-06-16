package com.dkproject.todoapp.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dkproject.todo.domain.model.CategoryInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "category_data")
class CategoryDataStore @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {
    companion object{
        private val CATEGORY_KEY = stringPreferencesKey("category_data")
    }

    suspend fun saveCategoryData(categoryData:List<CategoryInfo>){
        val categoryDataString = gson.toJson(categoryData)
        context.dataStore.edit {mutablePreferences->
            mutablePreferences[CATEGORY_KEY] = categoryDataString
        }
    }

    fun getCategoryData(): Flow<List<CategoryInfo>>{
        return context.dataStore.data.map { preferences ->
            gson.fromJson(preferences[CATEGORY_KEY], object:TypeToken<List<CategoryInfo>>(){}.type) ?: emptyList()
        }
    }


}
