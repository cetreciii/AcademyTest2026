package com.example.academytest2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.example.academytest2026.ui.ItemsListScreen
import com.example.academytest2026.ui.ItemsListViewModel
import com.example.academytest2026.ui.theme.AcademyTest2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcademyTest2026Theme {
                // ContentScreen() will replace this in feature/navigation
                val viewModel = remember { ItemsListViewModel() }
                var selectedItemId by remember { mutableStateOf(viewModel.selectedItemId) }

                ItemsListScreen(
                    items = viewModel.sortedItems,
                    selectedItemId = selectedItemId,
                    onItemClick = { id ->
                        viewModel.selectItem(id)
                        selectedItemId = viewModel.selectedItemId
                    },
                    onToggleFavorite = { item -> viewModel.toggleFavorite(item) },
                    onDeleteItem = { item -> viewModel.deleteItems(setOf(item.id)) },
                    onAddItem = { name -> viewModel.addItem(name) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPreview() {
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