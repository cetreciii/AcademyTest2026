# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project goal

This repository contains the **reference SwiftUI app** for a job application exercise. The task is to rewrite it as an equivalent **Android app in Kotlin + Jetpack Compose**, preserving identical functionality, including Previews. The Android project should live in this same repository.

## Branch strategy

- `develop` — default working branch. All feature branches are cut from here and merged back via PR.
- `production` — stable branch. Only receives PRs from `develop`. Both branches are protected (direct pushes blocked).

New work should always be done on a feature branch off `develop`, then submitted as a PR targeting `develop`.

## SwiftUI source app

### Build & Run

Open `AcademyTest.xcodeproj` in Xcode and run with `Cmd+R`. No CLI build system or test suite — all building and testing is done through Xcode.

### Architecture

MVVM with Swift's `@Observable` macro (iOS 17+). No persistence — state is in-memory only.

**Data flow:**
- `ItemsListViewModel` is the single source of truth, owned by `ContentView` as a `@State` property.
- It holds an array of `ItemViewModel` objects (each `@Observable`, `Identifiable`, `Hashable`).
- Views receive the view model via direct parameter passing (not environment), and bind with `@Bindable` where mutation is needed.
- `AddItemViewModel` is ephemeral — owned by `AddItemView` only for the duration of the sheet.

**Navigation:**
- `ContentView` uses `NavigationSplitView` (list + detail).
- Selection is driven by `ItemsListViewModel.selectedItemID` (a `UUID?`), not a separate navigation path.
- Deletion from the detail view calls `viewModel.deleteFromDetail(_:)`, which also clears `selectedItemID`.

**Sorting:**
- `ItemsListViewModel.sortedItems` sorts alphabetically (case-insensitive), breaking ties by `creationIndex` (insertion order).

**UI language:** Italian (labels, navigation titles, empty states).

## Android target app

### SwiftUI → Kotlin/Compose translation map

| SwiftUI source | Kotlin/Compose equivalent |
|---|---|
| `ItemViewModel.swift` | `data class Item(val id: UUID, val name: String, val isFavorite: Boolean)` |
| `ItemsListViewModel.swift` | `class ItemsListViewModel : ViewModel()` with `mutableStateListOf<Item>()` |
| `AddItemViewModel.swift` | Local `ViewModel` or `remember`-based state in the sheet |
| `ContentView.swift` | `ContentScreen` with `NavHost` or `NavigationSuiteScaffold` |
| `ItemsListView.swift` | `ItemsListScreen` with `LazyColumn` + swipe-to-delete via `SwipeToDismissBox` |
| `ItemDetailView.swift` | `ItemDetailScreen` with `Column` layout and `TopAppBar` delete action |
| `AddItemView.swift` | `AddItemSheet` using `ModalBottomSheet` |
| `ItemRowView.swift` | `ItemRow` composable |
| `FavoriteButton.swift` | `FavoriteButton` composable |
| `#Preview` | `@Preview(showBackground = true)` |

### Key SwiftUI → Compose concept translations

- `@State` / `@Observable` → `ViewModel` + `mutableStateOf` / `mutableStateListOf`, collected with `collectAsState()`
- `@Bindable` (two-way binding) → state hoisting: pass `value` down and `onValueChange` lambda up
- `NavigationSplitView` → `NavigationSuiteScaffold` (adaptive) or `NavHost` + `NavController`
- `.sheet(item:)` → `ModalBottomSheet` gated by a `Boolean` state
- `List` + `.onDelete` → `LazyColumn` + `SwipeToDismissBox`
- `Form` / `Section` → `Column` with manual grouping
- `ContentUnavailableView` → custom empty-state composable
- `ToolbarItem` → `TopAppBar(actions = { ... })`
- `@Environment(\.dismiss)` → `navController.popBackStack()` or a dismiss callback lambda

### Required Gradle dependencies

```kotlin
implementation(platform("androidx.compose:compose-bom:2024.XX.XX"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
implementation("androidx.navigation:navigation-compose")
// For adaptive split-view layout:
implementation("androidx.compose.material3.adaptive:adaptive")
```

### State management pattern to follow

The SwiftUI source mutates `ItemViewModel` objects directly (they are reference types via `@Observable`). In Compose, treat `Item` as an immutable `data class` and replace it in the list on mutation — this is the idiomatic pattern with `mutableStateListOf`.
