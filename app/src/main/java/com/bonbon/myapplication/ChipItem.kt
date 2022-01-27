package com.bonbon.myapplication

import com.bonbon.library.FilterableEntity

data class ChipItem(
    val value: String = ""
) : FilterableEntity {
    override fun filter(query: String): Boolean {
        return value.startsWith(query, ignoreCase = true)
    }
}