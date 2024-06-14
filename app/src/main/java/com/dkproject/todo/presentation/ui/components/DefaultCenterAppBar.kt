package com.dkproject.todoapp.presentation.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultCenterAppBar(
    title: String,
    navigateClick: () -> Unit,
    navigateIcon: ImageVector,
    navigateDescription: String,
    actionText:String?=null,
    actionClick:()->Unit,
) {
    CenterAlignedTopAppBar(title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = navigateClick) {
                Icon(imageVector = navigateIcon, contentDescription = navigateDescription)
            }
        },
        actions = {
            if(!actionText.isNullOrEmpty()){
                TextButton(onClick = actionClick) {
                    Text(text = actionText)  
                }
            }
        })
}

