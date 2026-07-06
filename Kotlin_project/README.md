# AcademyTest 2026 — Android

An Android app built with **Kotlin + Jetpack Compose**, functionally equivalent to the reference SwiftUI iOS app. Manage a list of items, mark favorites, and view item details — all in Italian.

---

## Screenshots

> Open the project in Android Studio and use the **Preview** panel to see each screen without running an emulator. Every composable ships with `@Preview` annotations.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.2.10 |
| UI | Jetpack Compose (BOM `2026.02.01`) |
| Design system | Material 3 |
| Architecture | MVVM — state hoisting, no side-effects in composables |
| Navigation | Navigation Compose `2.8.5` |
| ViewModel | `lifecycle-viewmodel-compose:2.8.7` |
| Min SDK | 26 (Android 8.0) |
| Target / Compile SDK | 37 |

---

## Project Structure

```
app/src/main/java/com/example/academytest2026/
├── model/
│   └── Item.kt                  # Immutable data class (id, name, isFavorite, creationIndex)
└── ui/
    ├── ItemsListViewModel.kt    # Single source of truth — owns the item list and selection state
    ├── ContentScreen.kt         # Root composable: NavHost wiring list ↔ detail
    ├── ItemsListScreen.kt       # LazyColumn with swipe-to-delete and empty state
    ├── ItemDetailScreen.kt      # Detail view with TopAppBar, Card form, delete action
    ├── AddItemSheet.kt          # ModalBottomSheet for creating a new item
    ├── ItemRow.kt               # Single row composable used inside the list
    ├── FavoriteButton.kt        # Reusable star toggle (filled = gold, outlined = grey)
    └── theme/                   # Generated Material 3 color/typography theme
```

---

## Architecture

### Data model

`Item` is an **immutable `data class`**. Mutations always produce a new copy:

```kotlin
// toggle favorite
items[index] = item.copy(isFavorite = !item.isFavorite)
```

Never mutate fields in-place — Compose won't detect the change.

### State management

`ItemsListViewModel` is the single source of truth. It holds:

| Property | Type | Description |
|---|---|---|
| `items` | `mutableStateListOf<Item>` | Live item list, pre-populated with 3 defaults |
| `selectedItemId` | `UUID?` | Currently selected item (drives navigation) |
| `sortedItems` | `List<Item>` | Alphabetical sort (case-insensitive), ties broken by `creationIndex` |
| `selectedItem` | `Item?` | Convenience getter from `selectedItemId` |

Public mutations: `addItem`, `toggleFavorite`, `deleteItems`, `deleteFromDetail`, `selectItem`.

### Data flow

```
ContentScreen (owns ViewModel)
    │
    ├── ItemsListScreen(items, onItemClick, onToggleFavorite, onDeleteItem, onAddItem)
    │       └── AddItemSheet(onSave, onDismiss)
    │
    └── ItemDetailScreen(item, onToggleFavorite, onDelete, onBack)
```

All mutations flow **up** via lambda callbacks. No child composable writes state directly.

### Navigation

`ContentScreen` hosts a `NavHost` with two destinations:

- `"list"` — `ItemsListScreen`
- `"detail"` — `ItemDetailScreen` (rendered only when `selectedItem != null`)

Tapping a row calls `viewModel.selectItem(id)` then `navController.navigate("detail")`. Deleting from the detail view calls `viewModel.deleteFromDetail(item)` then `navController.popBackStack()`, which clears `selectedItemId` and returns to the list.

---

## Getting Started

### Prerequisites

- Android Studio Meerkat (2024.3) or later
- JDK 17+
- Android SDK 37

### Run

1. Open the `Kotlin_project/` directory in Android Studio.
2. Let Gradle sync complete.
3. Select a device or emulator (API 26+).
4. Press **Run** (`Shift+F10`).

### Build from CLI

From the `Kotlin_project/` directory:

```bash
# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

The output APK is at `app/build/outputs/apk/debug/app-debug.apk`.

---

## Features

- **Item list** — scrollable list sorted alphabetically; swipe left to delete any row.
- **Add item** — bottom sheet with an auto-focused text field; confirm with "Salva" or the keyboard Done action.
- **Favorite toggle** — star button on each row and on the detail screen; gold = favorite.
- **Item detail** — card-based form showing name and favorite status; red delete button in the top bar navigates back after deletion.
- **Empty state** — illustrated placeholder with a shortcut button to open the add sheet.

---

## Key Constraints

- `Item` is immutable — always use `.copy(...)` to update fields.
- All UI text is in **Italian** (labels, titles, empty states, button copy).
- No persistence — state is in-memory only and resets on process death.
- `@Preview` composables must compile and render without a running device; keep previews free of side-effects.
