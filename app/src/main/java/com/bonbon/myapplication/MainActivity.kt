package com.bonbon.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bonbon.library.ActionChip
import com.bonbon.library.TriggerSeparator
import com.bonbon.library.textchipviews.MaterialTextChipView
import com.bonbon.library.textchipviews.OutLinedTextChipView
import com.bonbon.myapplication.ui.theme.ComposechipTheme
import com.google.accompanist.insets.ProvideWindowInsets


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposechipTheme {
                ProvideWindowInsets(
                ) {
                    val scrollState = rememberScrollState()
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Box(
                            modifier = Modifier.scrollable(
                                scrollState,
                                orientation = Orientation.Vertical
                            )
                        ) {
                            Row {
                                Text(text = "To", modifier = Modifier.width(50.dp))
                                MaterialChipTextViewDemo()
                            }
                        }

                        Box(
                            modifier = Modifier.scrollable(
                                scrollState,
                                orientation = Orientation.Vertical
                            )
                        ) {
                            Row {
                                Text(text = "To", modifier = Modifier.width(50.dp))
                                OutLineChipTextViewDemo()
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun OutLineChipTextViewDemo() {
    var text by remember {
        mutableStateOf("")
    }

    val items = remember {
        mutableStateListOf(
            ChipItem("Adam", R.drawable.ic_baseline_android_24),
            ChipItem("Eve"),
            ChipItem("Ken"),
            ChipItem("William"),
            ChipItem("Julien"),
        )
    }

    val selectedItems = remember {
        mutableStateListOf<ChipItem>()
    }

    OutLinedTextChipView(
        modifier = Modifier.padding(4.dp),
        searchableItems = items,
        chipItems = selectedItems,
        shape = RoundedCornerShape(6.dp),
        text = text,
        onValueChange = {
            text = if (it.trim().isNotEmpty() && it.contains(TriggerSeparator.Space.value)) {
                val trimmedText = text.trim()
                selectedItems.add(ChipItem(trimmedText))
                ""
            } else {
                it
            }
        },
        chipContent = {
            ActionChip(
                text = it.value,
                closeIcon = rememberVectorPainter(image = Icons.Default.Close),
                avatar = it.icon?.let { it1 -> painterResource(id = it1) },
                shape = RoundedCornerShape(18.dp)
            ) {
                selectedItems.remove(it)
            }
        },
        onKeyEvent = {
            if (it.key == Key.Backspace) {
                if (text.isEmpty() && selectedItems.count() > 0) {
                    selectedItems.removeAt(0.coerceAtLeast(selectedItems.count() - 1))
                }
            }
        },
        textStyle = TextStyle.Default.copy(color = Color.Red)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selectedItems.add(it)
            }
            .padding(8.dp)) {
            Text(text = it.value)
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun MaterialChipTextViewDemo() {
    var text by remember {
        mutableStateOf("")
    }

    val items = remember {
        mutableStateListOf(
            ChipItem("Adam", R.drawable.ic_baseline_android_24),
            ChipItem("Eve"),
            ChipItem("Ken"),
            ChipItem("William"),
            ChipItem("Julien"),
        )
    }

    val selectedItems = remember {
        mutableStateListOf<ChipItem>()
    }

    MaterialTextChipView(
        modifier = Modifier.padding(4.dp, end = 100.dp),
        searchableItems = items,
        chipItems = selectedItems,
        shape = RoundedCornerShape(6.dp),
        text = text,
        textStyle = TextStyle.Default.copy(fontSize = 12.sp),
        onValueChange = {
            text = if (it.trim().isNotEmpty() && it.contains(TriggerSeparator.Space.value)) {
                val trimmedText = text.trim()
                items.add(ChipItem(trimmedText))
                selectedItems.add(ChipItem(trimmedText))
                ""
            } else {
                it
            }
        },
        chipContent = {

            // can create your own buy using BonChip
            ActionChip(
                text = it.value,
                closeIcon = rememberVectorPainter(image = Icons.Default.Close),
                avatar = it.icon?.let { it1 -> painterResource(id = it1) },
                shape = RoundedCornerShape(18.dp),
            ) {
                selectedItems.remove(it)
            }
        },
        onKeyEvent = {
            if (it.key == Key.Backspace) {
                if (text.isEmpty() && selectedItems.count() > 0) {
                    selectedItems.removeAt(0.coerceAtLeast(selectedItems.count() - 1))
                }
            }
        },
        cursorBrush = SolidColor(Color.Red)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selectedItems.add(it)
            }
            .padding(8.dp)) {
            Text(text = it.value)
        }
    }
}