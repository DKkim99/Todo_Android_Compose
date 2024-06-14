package com.dkproject.todoapp.presentation.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Shape


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    colors: TextFieldColors = TextFieldDefaults.colors(),
    isSingleLine: Boolean,
    shape: Shape = OutlinedTextFieldDefaults.shape,
    placeholder: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester? = null,
    onDone: (() -> Unit) = {}
) {


    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = isSingleLine,
        onValueChange = onValueChange,
        colors = colors,
        shape = shape,
        placeholder = placeholder,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions {
            onDone()
        }
    )
    LaunchedEffect(Unit) {
        focusRequester?.requestFocus()
    }

}