package com.dkproject.todo.presentation.ui.screen.Detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.todo.domain.model.CategoryInfo
import com.dkproject.todo.domain.usecase.GetTodoByIdUseCase
import com.dkproject.todo.presentation.model.TodoUiModel
import com.dkproject.todo.presentation.model.toDomainModel
import com.dkproject.todo.presentation.model.toUiModel
import com.dkproject.todoapp.domain.model.Todo
import com.dkproject.todoapp.domain.usecase.GetCategoryUseCase
import com.dkproject.todoapp.domain.usecase.UpdateTodoUseCase
import com.dkproject.todoapp.presentation.ui.screen.EditCategory.EditCategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {

    val categoryList : StateFlow<List<CategoryInfo>> = getCategoryUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    lateinit var  uiState : StateFlow<TodoUiModel>

    fun getTodoById(id:Int){
        uiState = getTodoByIdUseCase(id)
            .filterNotNull()
            .map { it.toUiModel() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TodoUiModel()
            )
    }

    fun updateTodo(todo : TodoUiModel){
        viewModelScope.launch {
            updateTodoUseCase(todo.toDomainModel())
        }
    }
}