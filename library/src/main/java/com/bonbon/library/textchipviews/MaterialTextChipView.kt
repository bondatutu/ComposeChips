package com.bonbon.library

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
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
import com.bonbon.library.model.FilterableEntity

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun <T> MaterialTextChipView(
    modifier: Modifier = Modifier,
    textPadding: Dp = 8.dp,
    searchableItems: List<T>,
    chipItems: List<T>,
    text: String,
    shape: Shape = MaterialTheme.shapes.medium,
    baseLineSpacing: Dp = 2.dp,
    baseLineColor: Color = MaterialTheme.colors.primary,
    focused: Boolean = false,
    onValueChange: (String) -> Unit,
    onKeyEvent: (KeyEvent) -> Unit,
    chipContent: @Composable (T) -> Unit,
    dropDownContent: @Composable (T) -> Unit
) where T : FilterableEntity {

    var filteredItems by remember {
        mutableStateOf(searchableItems.distinct())
    }

    var isFocused by remember {
        mutableStateOf(focused)
    }

    val focusRequester = FocusRequester()

    CoreChipView(
        modifier = modifier,
        textPadding = textPadding,
        filteredItems = filteredItems,
        chipItems = chipItems,
        isFocused = isFocused,
        shape = shape,
        chipContent = chipContent,
        dropDownContent = dropDownContent,
        focusRequester = focusRequester,
        onClicked = {
            isFocused = it
        }
    ) {
        BasicTextField(value = text, onValueChange = {
            filteredItems = searchableItems.filter { item ->
                item.filter(query = it)
            }
            isFocused = true
            onValueChange(it)

        }, modifier = Modifier
            .width(IntrinsicSize.Min)
            .defaultMinSize(15.dp)
            .padding(6.dp)
            .onFocusChanged {
                isFocused = it.isFocused || it.hasFocus || it.isCaptured
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