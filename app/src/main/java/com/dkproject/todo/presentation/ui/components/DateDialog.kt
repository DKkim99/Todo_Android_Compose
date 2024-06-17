package com.dkproject.todoapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.dkproject.todo.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    hour: Int,
    minute: Int,
    todayDate : (Long) = System.currentTimeMillis(),
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit,
    onTimeSelected: (Int, Int) -> Unit
) {


    val dateState = rememberDatePickerState(initialSelectedDateMillis = todayDate)
    var timeDialogVisible by remember { mutableStateOf(false) }

    if (timeDialogVisible) {
        TimeDialog(onDismiss = { timeDialogVisible = false },
            onTimeSelected = { h, m ->
                onTimeSelected(h, m)
            })
    }

    BasicAlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = DatePickerDefaults.shape,
            modifier = Modifier
                .requiredWidth(360.dp)
        ) {
            Column {
                DatePicker(
                    modifier = Modifier.padding(8.dp),
                    state = dateState,
                    title = null,
                    colors = DatePickerDefaults.colors(containerColor = Color.Transparent)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.padding(horizontal = 22.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp)
                        .padding(vertical = 16.dp)
                        .clickable { timeDialogVisible = true }
                ) {
                    Icon(imageVector = Icons.Default.AccessTime, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(id = R.string.time))
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = if (hour == 0 && minute == 0) stringResource(id = R.string.none) else
                            "${hour.toString().padStart(2, '0')}:${
                                minute.toString().padStart(2, '0')
                            }"
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    TextButton(onClick = {
                        dateState.selectedDateMillis?.let(onDateSelected)
                        onDismiss()
                    }) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
    }


}




