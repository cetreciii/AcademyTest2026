package com.example.academytest2026.model

import java.util.UUID

data class Item(
    val id: UUID = UUID.randomUUID(),
    val creationIndex: Int,
    val name: String,
    val isFavorite: Boolean
)
