package com.example.academytest2026.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.academytest2026.ui.theme.AcademyTest2026Theme

@Composable
fun ContentScreen(
    viewModel: ItemsListViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ItemsListScreen(
                items = viewModel.sortedItems,
                selectedItemId = viewModel.selectedItemId,
                onItemClick = { id ->
                    viewModel.selectItem(id)
                    navController.navigate("detail")
                },
                onToggleFavorite = { item -> viewModel.toggleFavorite(item) },
                onDeleteItem = { item -> viewModel.deleteItems(setOf(item.id)) },
                onAddItem = { name -> viewModel.addItem(name) }
            )
        }
        composable("detail") {
            val item = viewModel.selectedItem
            if (item != null) {
                ItemDetailScreen(
                    item = item,
                    onToggleFavorite = { viewModel.toggleFavorite(item) },
                    onDelete = {
                        viewModel.deleteFromDetail(item)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentScreenPreview() {
    AcademyTest2026Theme {
        ContentScreen()
    }
}
