package com.example.academytest2026.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.academytest2026.model.Item
import java.util.UUID

class ItemsListViewModel : ViewModel() {

    val items = mutableStateListOf<Item>().also { it.addAll(defaultItems) }

    var selectedItemId: UUID? by mutableStateOf(null)
        private set

    private var nextCreationIndex = defaultItems.maxOf { it.creationIndex } + 1

    val sortedItems: List<Item>
        get() = items.sortedWith(
            compareBy<Item> { it.name.lowercase() }
                .thenBy { it.creationIndex }
        )

    val selectedItem: Item?
        get() = selectedItemId?.let { id -> items.find { it.id == id } }

    fun selectItem(id: UUID) {
        selectedItemId = id
    }

    fun addItem(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        items.add(
            Item(
                creationIndex = nextCreationIndex++,
                name = trimmed,
                isFavorite = false
            )
        )
    }

    fun toggleFavorite(item: Item) {
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) items[index] = item.copy(isFavorite = !item.isFavorite)
    }

    fun deleteItems(ids: Set<UUID>) {
        items.removeAll { it.id in ids }
        if (selectedItemId in ids) {
            selectedItemId = sortedItems.firstOrNull()?.id
        }
    }

    fun deleteFromDetail(item: Item) {
        items.removeAll { it.id == item.id }
        selectedItemId = null
    }

    companion object {
        val defaultItems = listOf(
            Item(creationIndex = 0, name = "Lupo 🐺", isFavorite = true),
            Item(creationIndex = 1, name = "Giraffa 🦒", isFavorite = false),
            Item(creationIndex = 2, name = "Leone 🦁", isFavorite = false)
        )
    }
}
