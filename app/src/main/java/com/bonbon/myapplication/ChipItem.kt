package com.bonbon.myapplication

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.bonbon.library.FilterableEntity

data class ChipItem(
    val value: String = "",
    val icon: Int? = null
) : FilterableEntity {
    override fun filter(query: String): Boolean {
        return value.startsWith(query, ignoreCase = true)
    }
}