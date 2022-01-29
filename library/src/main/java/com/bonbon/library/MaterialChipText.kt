package com.bonbon.library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun <T> MaterialChipText(
    modifier: Modifier = Modifier,
    textPadding: Dp = 8.dp,
    searchableItems: List<T>,
    chipItems: List<T>,
    text: String,
    shape: Shape = MaterialTheme.shapes.medium,
    borderStroke: BorderStroke = BorderStroke(2.dp, MaterialTheme.colors.primary),
    baseLineSpacing: Dp = 2.dp,
    baseLineColor: Color = MaterialTheme.colors.primary,
    onValueChange: (String) -> Unit,
    onKeyEvent: (KeyEvent) -> Unit,
    chipContent: @Composable (T) -> Unit,
    dropDownContent: @Composable (T) -> Unit
) where T : FilterableEntity {

    var filteredItems by remember {
        mutableStateOf(searchableItems.distinct())
    }

    var isFocused by remember {
        mutableStateOf(false)
    }

    val focusRequester = FocusRequester()

    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier
                .padding(textPadding)
                .fillMaxWidth()
                .clickable {
                    focusRequester.requestFocus()
                }, verticalArrangement = Arrangement.Center
        ) {
            FlowRow {
                ChipGroup(items = chipItems) {
                    chipContent(it)
                }

                BasicTextField(value = text, onValueChange = {
                    filteredItems = searchableItems.filter { item ->
                        item.filter(query = it)
                    }
                    onValueChange(it)

                }, modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .defaultMinSize(15.dp)
                    .padding(6.dp)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }
                    .onKeyEvent {
                        onKeyEvent(it)
                        false
                    }
                    .focusRequester(focusRequester = focusRequester))

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = baseLineSpacing),
                    color = baseLineColor
                )

            }
        }
        Spacer(modifier = Modifier.padding(1.dp))

        AnimatedVisibility(visible = isFocused) {
            LazyColumn(
                modifier = Modifier
                    .border(borderStroke, shape)
                    .fillMaxWidth(),
                content = {
                    items(items = filteredItems, itemContent = { item ->
                        dropDownContent(item)
                    })
                }
            )
        }
    }
}