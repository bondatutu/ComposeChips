package com.bonbon.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ActionChip(
    text: String,
    modifier: Modifier = Modifier,
    avatar: ImageVector? = null,
    color: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    textPadding: PaddingValues = PaddingValues(4.dp),
    textStyle: TextStyle = LocalTextStyle.current,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    closeIcon: ImageVector,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    onClose: () -> Unit
) {
    ActionChip(
        text = text,
        modifier = modifier,
        avatar = if (avatar != null) rememberVectorPainter(avatar) else null,
        elevation = elevation,
        shape = shape,
        color = color,
        border = border,
        closeIcon = rememberVectorPainter(closeIcon),
        tint = tint,
        textPadding = textPadding,
        textStyle = textStyle
    ) {
        onClose()
    }
}

@Composable
fun ActionChip(
    text: String,
    modifier: Modifier = Modifier,
    avatar: Painter? = null,
    color: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    textPadding: PaddingValues = PaddingValues(4.dp),
    textStyle: TextStyle = LocalTextStyle.current,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    closeIcon: Painter,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    onClose: () -> Unit
) {
    Chip(
        modifier = modifier,
        elevation = elevation,
        shape = shape,
        color = color,
        border = border,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = text,
                    style = textStyle,
                    modifier = Modifier.padding(textPadding)
                )

                Icon(
                    closeIcon,
                    contentDescription = "Close Chip",
                    modifier = Modifier
                        .size(14.dp)
                        .clickable {
                            onClose()
                        },
                    tint = tint
                )
            }
        })
}