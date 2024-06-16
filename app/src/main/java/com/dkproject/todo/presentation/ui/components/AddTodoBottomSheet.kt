package com.dkproject.todoapp.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.dkproject.todo.R
import com.dkproject.todo.presentation.ui.screen.home.CategoryViewModel
import com.dkproject.todo.presentation.util.ColorData
import com.dkproject.todoapp.domain.model.Todo
import com.dkproject.todoapp.presentation.util.dateFormatter
import com.dkproject.todoapp.presentation.util.getTodayDateWithoutTime
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    uploadTodo: (Todo) -> Unit,
) {
    val categories by viewModel.categoryList.collectAsState()
    var expandedCategory by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val focusRequester = remember { FocusRequester() }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var todoValue by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var todoDate by remember { mutableStateOf(getTodayDateWithoutTime()) }
    var hour by remember { mutableIntStateOf(0) }
    var minute by remember { mutableIntStateOf(0) }
    var categoryColor by remember { mutableIntStateOf(ColorData.skyBlue) }
    var visibleDatePicker by remember { mutableStateOf(false) }
    if (visibleDatePicker) {
        DateDialog(
            hour = hour,
            minute = minute,
            onDismiss = { visibleDatePicker = false },
            onDateSelected = {
                todoDate = it
            },
            onTimeSelected = { h, m ->
                hour = h
                minute = m
            })
    }



    ModalBottomSheet(
        modifier = Modifier,
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
    ) {
        Column(
            modifier = Modifier
        )
        {
            CustomTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .padding(horizontal = 18.dp),
                value = todoValue,
                isSingleLine = true,
                onValueChange = { todoValue = it },
                placeholder = { Text(text = stringResource(id = R.string.writetodo)) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                focusRequester = focusRequester
            )

            Row(
                modifier = Modifier.padding(horizontal = 12.dp),// 키보드 및 내비게이션 바 패딩 처리,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AssistChip(
                    onClick = { expandedCategory = true },
                    label = {
                        Text(
                            text = if (category.isEmpty()) stringResource(id = R.string.nocategory) else category,
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = AssistChipDefaults.assistChipBorder(enabled = false)
                )
                DropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false }) {
                    categories.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it.category) },
                            onClick = {
                                category = it.category
                                categoryColor = it.color
                                expandedCategory = false
                            })
                    }
                }
                AssistChip(
                    onClick = {
                        visibleDatePicker = true
                    },
                    label = {
                        Text(text = dateFormatter(todoDate))
                    },
                    colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = AssistChipDefaults.assistChipBorder(enabled = false)
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        if (todoValue.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(message = context.getString(R.string.inputtitle))
                            }
                            return@IconButton
                        }
                        uploadTodo(
                            Todo(
                                title = todoValue,
                                category = category,
                                date = todoDate,
                                hour = hour,
                                minute = minute,
                                categoryColor = categoryColor
                            )
                        )

                    }) {
                    Icon(imageVector = Icons.Default.Upload, contentDescription = null)
                }
            }
            AnimatedVisibility(visible = snackbarHostState.currentSnackbarData != null) {
                SnackbarHost(hostState = snackbarHostState)
            }
        }
    }
}


