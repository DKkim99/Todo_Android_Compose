package com.dkproject.todoapp.presentation.ui.screen.EditCategory

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.dkproject.todo.R
import com.dkproject.todo.domain.model.CategoryInfo
import com.dkproject.todo.presentation.ui.components.AddCategoryDialog
import com.dkproject.todoapp.presentation.ui.components.DefaultCenterAppBar
import java.util.Locale.Category


@Composable
fun EditCategoryScreen(
    viewModel: EditCategoryViewModel,
    onBack: () -> Unit,
) {
    val categoryList by viewModel.categoryList.collectAsState()
    Log.d("categoryList", categoryList.toString())
    var addCategoryVisible by remember { mutableStateOf(false) }

    var isEditMode by remember { mutableStateOf(false) }
    var categoryUid by remember { mutableStateOf("") }
    var beforeCategoryText by remember { mutableStateOf("") }
    var categoryText by remember { mutableStateOf("") }
    var categoryColor by remember { mutableIntStateOf(0) }

    if (addCategoryVisible) {
        AddCategoryDialog(
            addCategory = { categoryInfo ->
                viewModel.setCategory(categoryInfo, beforeCategoryText = beforeCategoryText)
                categoryUid = ""
                categoryText = ""
                categoryColor = 0
                isEditMode = false
                addCategoryVisible = false
            },
            onDismissRequest = { addCategoryVisible = false },
            isEditMode = isEditMode,
            editCategoryUid = categoryUid,
            editCategoryText = categoryText,
            editCategoryColor = categoryColor,
            beforeChangeCategory = {beforeCategoryText = it}
        )
    }
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
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AnimatedVisibility(visible = categoryList.isNotEmpty()) {
                LazyColumn {
                    items(categoryList) {
                        CategoryItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 18.dp, vertical = 12.dp),
                            category = it,
                            deleteCategory = { categoryInfo ->
                                viewModel.delteCategory(categoryInfo)
                            }, editCategory = { categoryInfo ->
                                isEditMode = true
                                categoryUid = categoryInfo.uid
                                categoryText = categoryInfo.category
                                categoryColor = categoryInfo.color
                                addCategoryVisible = true
                            })
                    }

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { addCategoryVisible = true }
            ) {
                Icon(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.Blue
                )
                Text(
                    text = stringResource(id = R.string.addcategory),
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
                    color = Color.Blue
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: CategoryInfo,
    deleteCategory: (CategoryInfo) -> Unit,
    editCategory: (CategoryInfo) -> Unit,
) {
    Log.d("CategoryItem", category.toString())
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier.size(14.dp),
            color = Color(category.color)
        ) {}
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = category.category)
        Spacer(modifier = Modifier.weight(1f))
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(x = (-40).dp, y = 0.dp)
            ) {
                DropdownMenuItem(text = { Text(text = "수정하기") }, onClick = {
                    expanded = false
                    editCategory(category)
                })
                DropdownMenuItem(
                    text = { Text(text = "삭제하기") },
                    onClick = {
                        expanded = false
                        deleteCategory(category) }
                )
            }
        }
    }
}