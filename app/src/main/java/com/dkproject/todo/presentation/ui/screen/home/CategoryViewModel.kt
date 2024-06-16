package com.dkproject.todo.presentation.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.todo.domain.model.CategoryInfo
import com.dkproject.todoapp.domain.usecase.GetCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "EditCategoryViewModel"
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val categoryList : StateFlow<List<CategoryInfo>> = getCategoryUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(CategoryViewModel.TIMEOUT_MILLIS), listOf())
}