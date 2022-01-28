package com.bonbon.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bonbon.library.ActionChip
import com.bonbon.library.ChipText
import com.bonbon.library.TriggerSeparator
import com.bonbon.myapplication.ui.theme.ComposechipTheme

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposechipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    Row {
                        Text(text = "To")
                        ChipTextView()
                    }
                }
            }
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun ChipTextView() {
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

    ChipText(
        modifier = Modifier.padding(4.dp),
        searchableItems = items,
        chipItems = selectedItems,
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
                avatar = it.icon?.let { it1 -> painterResource(id = it1) }
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
        }
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