package com.dkproject.todoapp.presentation.ui.screen.Home

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.todo.R
import com.dkproject.todo.presentation.activity.DetailActivity
import com.dkproject.todo.presentation.ui.theme.TodoTheme
import com.dkproject.todoapp.domain.model.Todo
import com.dkproject.todoapp.presentation.activity.EditCategoryActivity
import com.dkproject.todoapp.presentation.ui.components.AddTodoBottomSheet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
) {
    val context = LocalContext.current
    val todoState by viewModel.homeUiState.collectAsState()
    var addTodoVisible by rememberSaveable { mutableStateOf(false) }
    var expaneded by rememberSaveable { mutableStateOf(false) }
    if (addTodoVisible) {
        AddTodoBottomSheet(onDismiss = { addTodoVisible = false },
            uploadTodo = { todo ->
                viewModel.insertTodo(todo)
                addTodoVisible = false
            })
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.todo)) },
            actions = {
                IconButton(onClick = { expaneded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
                DropdownMenu(expanded = expaneded, onDismissRequest = { expaneded = false }) {
                    DropdownMenuItem(text = { Text(text = stringResource(id = R.string.setcategory)) },
                        onClick = {
                            expaneded = false
                            context.startActivity(
                                Intent(
                                    context,
                                    EditCategoryActivity::class.java
                                )
                            )
                        })
                }

            })
    },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(innerPadding),
                onClick = { addTodoVisible = true },
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.addtodo)
                )
            }
        }) { innerScaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerScaffoldPadding)
                .padding(horizontal = 12.dp)
        ) {
            TodoBody(
                modifier = Modifier.fillMaxSize(),
                todoList = todoState.todoList.sortedBy { it.category },
                updateTodo = {
                    viewModel.updateTodo(it)
                },
                todoDetail = { id ->
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra("todoId", id)
                    })
                }
            )
        }
    }
}

@Composable
fun TodoBody(
    modifier: Modifier = Modifier,
    todoList: List<Todo>,
    updateTodo: (Todo) -> Unit,
    todoDetail: (Int) -> Unit
) {
    Box(modifier = modifier) {
        if (todoList.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.nottodaytodo)
            )
        } else {
            TodoList(
                todoList = todoList.filter { !it.completed },
                completedTodoList = todoList.filter { it.completed },
                updateTodo = updateTodo,
                todoDetail = todoDetail
            )
        }
    }
}


@Composable
fun TodoList(
    todoList: List<Todo>,
    completedTodoList: List<Todo>,
    updateTodo: (Todo) -> Unit,
    todoDetail: (Int) -> Unit,
) {

    var visibleNotTodo by rememberSaveable { mutableStateOf(true) }
    var visibleCompletedTodo by rememberSaveable { mutableStateOf(true) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { visibleNotTodo = !visibleNotTodo },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.todaytodo)
            )
            if (visibleNotTodo) {
                Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = null)
            } else
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
        }
        AnimatedVisibility(visible = visibleNotTodo) {
            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                items(todoList) {
                    TodoItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { todoDetail(it.id) },
                        todo = it,
                        updateTodo = updateTodo
                    )
                }

            }
        }
        AnimatedVisibility(visible = completedTodoList.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { visibleCompletedTodo = !visibleCompletedTodo },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.todaycompletetodo)
                )
                if (visibleCompletedTodo) {
                    Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = null)
                } else
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
            }
        }
        AnimatedVisibility(visible = visibleCompletedTodo) {
            LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                items(completedTodoList) {
                    TodoItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { todoDetail(it.id) },
                        todo = it,
                        updateTodo = updateTodo
                    )
                }

            }
        }
    }


}


@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    todo: Todo,
    updateTodo: (Todo) -> Unit,
) {
    Log.d("TodoItem", todo.toString())
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                updateTodo(todo.copy(completed = !todo.completed))
            }) {
                Icon(
                    imageVector = if (todo.completed) Icons.Filled.Circle else Icons.Outlined.Circle,
                    contentDescription = null,
                    tint = Color(todo.categoryColor)
                )
            }
            Text(
                text = todo.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.weight(1f))

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeScreenPreview() {
    TodoTheme {
        HomeScreen(innerPadding = PaddingValues())
    }
}