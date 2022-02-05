package com.bonbon.library.textchipviews

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bonbon.library.corecomponent.CoreChipView
import com.bonbon.library.model.FilterableEntity

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun <T> OutLinedTextChipView(
    modifier: Modifier = Modifier,
    textPadding: Dp = 8.dp,
    searchableItems: List<T>,
    chipItems: List<T>,
    text: String,
    shape: Shape = MaterialTheme.shapes.medium,
    borderStroke: BorderStroke = BorderStroke(2.dp, MaterialTheme.colors.primary),
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


    CoreChipView(
        modifier = modifier.border(border = borderStroke, shape = shape),
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
                isFocused = it.isFocused
            }
            .onKeyEvent {
                onKeyEvent(it)
                false
            }
            .focusRequester(focusRequester = focusRequester))
    }
}