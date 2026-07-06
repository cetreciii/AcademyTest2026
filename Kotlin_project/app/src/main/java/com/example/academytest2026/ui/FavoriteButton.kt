package com.example.academytest2026.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.academytest2026.ui.theme.AcademyTest2026Theme

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggle: () -> Unit
) {
    IconButton(onClick = onToggle) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
            contentDescription = if (isFavorite) "Rimuovi dai preferiti" else "Aggiungi ai preferiti",
            tint = if (isFavorite) Color(0xFFFFD700) else Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteButtonFavoritePreview() {
    AcademyTest2026Theme {
        FavoriteButton(isFavorite = true, onToggle = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteButtonNotFavoritePreview() {
    AcademyTest2026Theme {
        FavoriteButton(isFavorite = false, onToggle = {})
    }
}
