package com.dkproject.todo.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.todo.presentation.ui.screen.Detail.DetailScreen
import com.dkproject.todo.presentation.ui.screen.Detail.DetailViewModel
import com.dkproject.todo.presentation.ui.theme.TodoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                val todoId = intent.getIntExtra("todoId",-1)
                Log.d("id", todoId.toString())
                if(todoId != -1){
                    val viewModel : DetailViewModel = hiltViewModel()
                    viewModel.getTodoById(todoId)
                    DetailScreen(viewModel = viewModel,onBack = {finish()})
                }else{
                    Box(modifier = Modifier.fillMaxSize()){
                        Text(text = "없음")
                    }
                }
            }
        }
    }
}