package com.bonbon.library.corecomponent

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bonbon.library.model.FilterableEntity
import com.google.accompanist.flowlayout.FlowRow

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
internal fun <T> CoreChipView(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    textPadding: Dp = 8.dp,
    filteredItems: List<T>,
    chipItems: List<T>,
    shape: Shape = MaterialTheme.shapes.medium,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    isFocused: Boolean = false,
    onClicked: (Boolean) -> Unit,
    chipContent: @Composable (T) -> Unit,
    dropDownContent: @Composable (T) -> Unit,
    textFieldContent: @Composable () -> Unit,
) where T : FilterableEntity {

    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        val boxWithConstraints = this
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(textPadding)
                    .fillMaxWidth()
                    .clickable {
                        onClicked(true)
                        keyboardController?.show()
                        focusRequester.requestFocus()

                    }, verticalArrangement = Arrangement.Center
            ) {
                FlowRow {
                    ChipGroup(items = chipItems) {
                        chipContent(it)
                    }
                    textFieldContent()
                }
            }

            Spacer(modifier = Modifier.padding(1.dp))

            PopupContent(
                width = boxWithConstraints.maxWidth,
                isTextFocused = isFocused,
                shape = shape,
                onDismissed = {
                    onClicked(false)
                }
            ) {
                LazyColumn(
                    modifier = Modifier
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
}