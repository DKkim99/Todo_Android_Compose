package com.dkproject.todo.presentation.ui.screen.Detail

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.dkproject.todo.R
import com.dkproject.todo.domain.model.CategoryInfo
import com.dkproject.todo.presentation.model.TodoUiModel
import com.dkproject.todo.presentation.ui.theme.customgray2
import com.dkproject.todoapp.presentation.ui.components.DateDialog
import com.dkproject.todoapp.presentation.util.dateFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBack: () -> Unit,
) {
    val todo by viewModel.uiState.collectAsState()
    val categorys by viewModel.categoryList.collectAsState()
    var todoData by remember { mutableStateOf(TodoUiModel()) }
    var categoryExpand by remember { mutableStateOf(false) }
    todoData = todo
    Log.d("DetailScreen", todo.toString())
    Scaffold(topBar = {
        TopAppBar(title = {}, navigationIcon = {
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(
                        id = R.string.back
                    )
                )
            }
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {
            CategorySection(
                categoryExpand = categoryExpand,
                categoryText = todoData.category,
                categoryColor = todoData.categoryColor,
                categorys = categorys,
                categoryChange = { categoryInfo ->
                    todoData = todoData.copy(
                        category = categoryInfo.category,
                        categoryColor = categoryInfo.color
                    )
                },
                categoryExpandChange = { expand ->
                    categoryExpand = expand
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = todoData.title,
                onValueChange = { todoData = todoData.copy(title = it) },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(18.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(18.dp))
            DateSection(
                modifier = Modifier.fillMaxWidth(),
                hour = todoData.hour,
                min = todoData.minute,
                date = todoData.date,
                updateDate = {date->
                    todoData = todoData.copy(date = date)
                },
                updateTime = { h, m ->
                    todoData = todoData.copy(hour = h, minute = m)
                }
            )

        }

    }
}

@Composable
fun CategorySection(
    categoryExpand: Boolean,
    categoryText: String?,
    categoryColor: Int,
    categorys: List<CategoryInfo>,
    categoryChange: (CategoryInfo) -> Unit,
    categoryExpandChange: (Boolean) -> Unit,
) {
    Box() {
        Surface(
            modifier = Modifier
                .clickable { categoryExpandChange(true) },
            shape = RoundedCornerShape(6.dp),
            color = Color(categoryColor)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = if (categoryText.isNullOrEmpty()) stringResource(id = R.string.nocategory) else categoryText
                        ?: "",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    modifier = Modifier.padding(end = 3.dp),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        }
        DropdownMenu(
            expanded = categoryExpand,
            onDismissRequest = { categoryExpandChange(false) }
        ) {
            categorys.forEach {
                DropdownMenuItem(text = { Text(text = it.category) }, onClick = {
                    categoryChange(it)
                    categoryExpandChange(false)
                })
            }
        }
    }
}


@Composable
fun DateSection(
    modifier: Modifier = Modifier,
    hour: Int,
    min: Int,
    date: Long,
    updateDate: (Long) -> Unit,
    updateTime: (Int, Int) -> Unit
) {
    var dateDialogVisible by remember { mutableStateOf(false) }
    if (dateDialogVisible) {
        DateDialog(hour = hour,
            minute = min,
            todayDate = date,
            onDismiss = { dateDialogVisible = false },
            onDateSelected = { date ->
                updateDate(date)
            },
            onTimeSelected = { h, m ->
                updateTime(h, m)
            }
        )
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = stringResource(id = R.string.deadline))
        Spacer(modifier = Modifier.weight(1f))
        AssistChip(
            onClick = { dateDialogVisible = true },
            label = { Text(text = dateFormatter(date)) })
    }
}