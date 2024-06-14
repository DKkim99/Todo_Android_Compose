package com.dkproject.todoapp.presentation.ui.screen.EditCategory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dkproject.todo.R
import com.dkproject.todoapp.presentation.ui.components.DefaultCenterAppBar


@Composable
fun EditCategoryScreen(
    viewModel: EditCategoryViewModel,
    onBack:()->Unit,
) {
    val categoryList = viewModel.categoryList.collectAsState()
    Scaffold(
        topBar = {
            DefaultCenterAppBar(
                title = stringResource(id = R.string.category),
                navigateClick = onBack,
                navigateIcon = Icons.AutoMirrored.Default.ArrowBack,
                navigateDescription = stringResource(id = R.string.back),
                actionText = stringResource(id = R.string.confirm),
                actionClick = onBack
            )
        }
    ) {innerPadding->
        Column(modifier= Modifier.padding(innerPadding)) {

        }
    }

}