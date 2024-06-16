package com.dkproject.todo.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.dkproject.todo.R
import com.dkproject.todo.domain.model.CategoryInfo
import com.dkproject.todo.presentation.ui.theme.customgray
import com.dkproject.todo.presentation.ui.theme.customgray2
import com.dkproject.todo.presentation.ui.theme.customlightgray
import com.dkproject.todo.presentation.util.ColorData
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryDialog(
    addCategory: ((CategoryInfo) -> Unit),
    onDismissRequest: () -> Unit,
    isEditMode: Boolean = false,
    editCategoryUid : String = "",
    editCategoryText: String = "",
    editCategoryColor: Int = 0,
    beforeChangeCategory : (String) -> Unit ={}
) {
    var categoryUid by remember { mutableStateOf(UUID.randomUUID().toString()) }
    var categoryText by remember { mutableStateOf("") }
    var selectedColor by remember { mutableIntStateOf(ColorData.skyBlue) }
    var expandColorSection by remember { mutableStateOf(false) }
    val categoryColors = ColorData.getColors()
    if (isEditMode) {
        beforeChangeCategory(editCategoryText)
        categoryUid = editCategoryUid
        categoryText = editCategoryText
        selectedColor = editCategoryColor
    }
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            shape = RoundedCornerShape(12.dp),
            color = customgray
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    text = stringResource(id = R.string.newcategory),
                    color = Color.White
                )
                Surface(
                    modifier = Modifier.padding(horizontal = 22.dp),
                    shape = RoundedCornerShape(12.dp), color = customgray2
                ) {
                    Box(modifier = Modifier) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .verticalScroll(rememberScrollState()),
                            value = categoryText,
                            onValueChange = {
                                if (it.length <= 30) categoryText = it
                            },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedTextColor = Color.White,
                                focusedTextColor = Color.White
                            )
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 8.dp),
                            text = stringResource(id = R.string.textcount, categoryText.length),
                            color = if (categoryText.length >= 30) Color.Red else customlightgray,
                            fontSize = 12.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                        .clickable { expandColorSection = !expandColorSection },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.categoryColor), color = Color.White)
                    Spacer(modifier = Modifier.weight(1f))
                    Surface(
                        modifier = Modifier.size(14.dp),
                        shape = CircleShape,
                        color = Color(selectedColor)
                    ) {}
                }
                AnimatedVisibility(visible = expandColorSection) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        categoryColors.forEach {
                            Surface(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { selectedColor = it },
                                shape = CircleShape,
                                color = Color(it)
                            ) {
                                if (it == selectedColor) {
                                    Surface(
                                        modifier = Modifier.padding(2.dp),
                                        shape = CircleShape,
                                        color = Color.Transparent,
                                        border = BorderStroke(2.dp, Color.Gray)
                                    ) {}
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onDismissRequest) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    TextButton(onClick = {
                        addCategory(
                            CategoryInfo(
                                uid = categoryUid,
                                category = categoryText,
                                color = selectedColor
                            )
                        )
                    }) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                }
            }
        }
    }
}