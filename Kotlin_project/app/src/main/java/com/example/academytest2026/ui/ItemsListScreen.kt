package com.example.academytest2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.academytest2026.model.Item
import com.example.academytest2026.ui.theme.AcademyTest2026Theme
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListScreen(
    items: List<Item>,
    selectedItemId: UUID?,
    onItemClick: (UUID) -> Unit,
    onToggleFavorite: (Item) -> Unit,
    onDeleteItem: (Item) -> Unit,
    onAddItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Oggetti") },
                actions = {
                    IconButton(onClick = { showAddSheet = true }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Aggiungi oggetto"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (items.isEmpty()) {
            EmptyItemsState(
                onAddItem = { showAddSheet = true },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(items, key = { it.id }) { item ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart) {
                                onDeleteItem(item)
                                true
                            } else false
                        }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = {
                            Box(
                                contentAlignment = Alignment.CenterEnd,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.errorContainer)
                                    .padding(horizontal = 20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Elimina",
                                    tint = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    ) {
                        ItemRow(
                            item = item,
                            onToggleFavorite = { onToggleFavorite(item) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .clickable { onItemClick(item.id) }
                                .padding(horizontal = 16.dp)
                        )
                    }
                    HorizontalDivider()
                }
            }
        }
    }

    if (showAddSheet) {
        AddItemSheet(
            onDismiss = { showAddSheet = false },
            onSave = { name ->
                onAddItem(name)
                showAddSheet = false
            }
        )
    }
}

@Composable
private fun EmptyItemsState(
    onAddItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(32.dp),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "🗂",
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Nessun oggetto",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Aggiungi un oggetto dalla barra in alto.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onAddItem) {
            Text("Aggiungi oggetto")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemsListScreenPreview() {
    AcademyTest2026Theme {
        ItemsListScreen(
            items = ItemsListViewModel.defaultItems,
            selectedItemId = null,
            onItemClick = {},
            onToggleFavorite = {},
            onDeleteItem = {},
            onAddItem = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemsListScreenEmptyPreview() {
    AcademyTest2026Theme {
        ItemsListScreen(
            items = emptyList(),
            selectedItemId = null,
            onItemClick = {},
            onToggleFavorite = {},
            onDeleteItem = {},
            onAddItem = {}
        )
    }
}
