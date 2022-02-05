package com.bonbon.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.bonbon.library.corecomponent.BonChip

@Composable
fun ActionChip(
    text: String,
    modifier: Modifier = Modifier,
    avatar: Painter? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    color: Color = MaterialTheme.colors.primary,
    shape: Shape = MaterialTheme.shapes.medium,
    textStyle: TextStyle = LocalTextStyle.current,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    closeIcon: Painter,
    onClose: () -> Unit
) {
    BonChip(
        modifier = modifier,
        elevation = elevation,
        shape = shape,
        color = color,
        border = border,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (avatar != null) {
                    Icon(
                        painter = avatar,
                        contentDescription = "avatar",
                        modifier = Modifier.size(24.dp)
                            .padding(start = 4.dp)
                            .border(1.dp, color, CircleShape)
                    )
                }
                Text(
                    text = text,
                    style = textStyle,
                    modifier = Modifier.padding(8.dp, 4.dp),
                    fontSize = fontSize
                )

                Icon(
                    closeIcon,
                    contentDescription = "Close Chip",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(18.dp)
                        .clickable {
                            onClose()
                        },

                )
            }
        })
}