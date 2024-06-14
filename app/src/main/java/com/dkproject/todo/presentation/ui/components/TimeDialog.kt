package com.dkproject.todoapp.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dkproject.todo.R
import com.vsnappy1.timepicker.TimePicker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDialog(
    onTimeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var hour by remember { mutableIntStateOf(0) }
    var minute by remember { mutableIntStateOf(0) }
    BasicAlertDialog(onDismissRequest = { /*TODO*/ }) {
        Surface(
            shape = DatePickerDefaults.shape,
        ) {
            Column {
                TimePicker(onTimeSelected = { h, m ->
                    Log.d("hour", h.toString())
                    Log.d("min", m.toString())
                    hour = h
                    minute = m
                })
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = onDismiss
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    TextButton(modifier = Modifier.weight(1f),
                        onClick = {
                            onTimeSelected(hour, minute)
                            onDismiss()
                        }) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                }
            }
        }
    }
}