package com.dkproject.todoapp.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.dkproject.todo.presentation.ui.theme.TodoTheme
import com.dkproject.todoapp.presentation.ui.screen.EditCategory.EditCategoryScreen
import com.dkproject.todoapp.presentation.ui.screen.EditCategory.EditCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCategoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                val viewModel: EditCategoryViewModel by viewModels()
                EditCategoryScreen(
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }
}