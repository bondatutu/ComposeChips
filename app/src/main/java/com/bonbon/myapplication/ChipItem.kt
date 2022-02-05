package com.bonbon.myapplication

import com.bonbon.library.model.FilterableEntity

data class ChipItem(
    val value: String = "",
    val icon: Int? = null
) : FilterableEntity {
    override fun filter(query: String): Boolean {
        return value.startsWith(query, ignoreCase = true)
    }
}