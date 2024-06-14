package com.dkproject.todoapp.presentation.ui.screen.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkproject.todoapp.domain.model.Todo
import com.dkproject.todoapp.domain.usecase.GetTodoByDateUseCase
import com.dkproject.todoapp.domain.usecase.InsertTodoUseCase
import com.dkproject.todoapp.domain.usecase.UpdateTodoUseCase
import com.dkproject.todoapp.presentation.util.getTodayDateWithoutTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertTodoUseCase: InsertTodoUseCase,
    private val getTodoByDateUseCase: GetTodoByDateUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {


    val homeUiState: StateFlow<HomeUiState> =
        getTodoByDateUseCase.invoke(getTodayDateWithoutTime()).map {
            HomeUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )


    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            insertTodoUseCase(todo)
        }
    }

    fun updateTodo(updatedTodo: Todo) {
        viewModelScope.launch {
            updateTodoUseCase(updatedTodo)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5000L
    }

}

data class HomeUiState(val todoList: List<Todo> = listOf())


