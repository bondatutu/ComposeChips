package com.bonbon.library.model

interface FilterableEntity {
    fun filter(query: String): Boolean
}