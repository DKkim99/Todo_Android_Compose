package com.dkproject.todoapp.presentation.ui.screen.EditCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.todo.domain.model.CategoryInfo
import com.dkproject.todo.domain.usecase.UpdateTodoCategoryAndColorUseCase
import com.dkproject.todoapp.domain.usecase.GetCategoryUseCase
import com.dkproject.todoapp.domain.usecase.SetCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase,
    private val setCategoryUseCase: SetCategoryUseCase,
    private val updateTodoCategoryAndColorUseCase: UpdateTodoCategoryAndColorUseCase
):ViewModel() {
    companion object {
        private const val TAG = "EditCategoryViewModel"
        private const val TIMEOUT_MILLIS = 5_000L
    }


    val categoryList : StateFlow<List<CategoryInfo>> = getCategoryUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), listOf())


    fun setCategory(category: CategoryInfo,beforeCategoryText:String) {
        viewModelScope.launch {
            val categoryList = categoryList.value.toMutableList()
            val existingCategoryIndex = categoryList.indexOfFirst {it.uid == category.uid}
            if(existingCategoryIndex != -1){
                categoryList[existingCategoryIndex] = category
                updateTodoCategoryAndColorUseCase(categoryName = beforeCategoryText, newCategoryName = category.category, newColor = category.color)
            }
            else
                categoryList.add(category)
            setCategoryUseCase(categoryList)
        }
    }

    fun updateTodo(){

    }

    fun delteCategory(category: CategoryInfo){
        viewModelScope.launch {
            val categoryList = categoryList.value.toMutableList()
            if(categoryList.contains(category))
                categoryList.remove(category)
            setCategoryUseCase(categoryList)
        }
    }

}



