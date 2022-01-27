package com.bonbon.library

interface FilterableEntity {
    fun filter(query: String): Boolean
}