package com.bonbon.library

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
internal fun PopupContent(
    width: Dp = Dp.Unspecified,
    isTextFocused: Boolean = false,
    elevation: Dp = 8.dp,
    shape: Shape = MaterialTheme.shapes.medium,
    onDismissed: ((Boolean) -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val offset = DpOffset(0.dp, 0.dp)

    val expandedStates = remember { MutableTransitionState(false) }
    val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }
    expandedStates.targetState = isTextFocused
    val transition = updateTransition(expandedStates, "DropDownMenu")

    val scale by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // Dismissed to expanded
                tween(
                    durationMillis = 120,
                    easing = LinearOutSlowInEasing
                )
            } else {
                // Expanded to dismissed.
                tween(
                    durationMillis = 1,
                    delayMillis = 75 - 1,
                    easing = LinearOutSlowInEasing
                )
            }
        }, label = ""
    ) {
        if (it) {
            // Menu is expanded.
            1f
        } else {
            // Menu is dismissed.
            0.8f
        }
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // Dismissed to expanded
                tween(durationMillis = 300)
            } else {
                // Expanded to dismissed.
                tween(durationMillis = 300)
            }
        }, label = ""
    ) {
        if (it) {
            // Menu is expanded.
            1f
        } else {
            // Menu is dismissed.
            0f
        }
    }

    if (expandedStates.currentState || expandedStates.targetState) {
        Popup(
            popupPositionProvider = DropdownMenuPositionProvider(
                offset,
                LocalDensity.current
            ),
            onDismissRequest = {
                onDismissed?.invoke(false)
            },
            properties = PopupProperties()
        ) {

            Box(
                modifier = Modifier.width(width = width),
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                this.alpha = alpha
                                transformOrigin = transformOriginState.value
                            },
                        elevation = elevation,

                        shape = shape
                    ) {
                        content()
                    }

                }
            }

        }
    }


}