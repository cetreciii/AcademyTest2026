package com.example.academytest2026.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.academytest2026.model.Item
import com.example.academytest2026.ui.theme.AcademyTest2026Theme

@Composable
fun ItemRow(
    item: Item,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = if (item.isFavorite) "Preferito" else "Non preferito",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        FavoriteButton(
            isFavorite = item.isFavorite,
            onToggle = onToggleFavorite
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemRowPreview() {
    AcademyTest2026Theme {
        LazyColumn {
            item {
                ItemRow(
                    item = Item(creationIndex = 0, name = "Lupo 🐺", isFavorite = true),
                    onToggleFavorite = {}
                )
            }
            item {
                ItemRow(
                    item = Item(creationIndex = 1, name = "Giraffa 🦒", isFavorite = false),
                    onToggleFavorite = {}
                )
            }
        }
    }
}
