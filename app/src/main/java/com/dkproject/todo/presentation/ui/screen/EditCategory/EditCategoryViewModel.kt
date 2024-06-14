package com.dkproject.todoapp.presentation.ui.screen.EditCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val setCategoryUseCase: SetCategoryUseCase
):ViewModel() {
    companion object {
        private const val TAG = "EditCategoryViewModel"
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val categoryList : StateFlow<List<String>> = getCategoryUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(TIMEOUT_MILLIS), emptyList())


    fun setCategory(category: String) {
        viewModelScope.launch {

        }
    }

}



