# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project goal

This repository contains a **reference SwiftUI app** and its **Android equivalent in Kotlin + Jetpack Compose**. The Android app must preserve identical functionality to the SwiftUI source, including Previews.

## Repository structure

```
Swift_project/    — iOS source app (AcademyTest.xcodeproj + AcademyTest/)
Kotlin_project/   — Android target app (Gradle project, package com.example.academytest2026)
```

## Branch strategy

- `develop` — default working branch. All feature branches are cut from here and merged back via PR.
- `production` — stable branch. Only receives PRs from `develop`. Both branches are protected (direct pushes blocked).

Feature branches are grouped by screen, not by file:

| Branch | Scope |
|---|---|
| `feature/data-model` | `Item.kt`, `ItemsListViewModel.kt` |
| `feature/items-list-screen` | `FavoriteButton.kt`, `ItemRow.kt`, `AddItemSheet.kt`, `ItemsListScreen.kt` |
| `feature/item-detail-screen` | `ItemDetailScreen.kt` |
| `feature/navigation` | `ContentScreen.kt`, updated `MainActivity.kt` |

## SwiftUI source app

### Build & Run

Open `Swift_project/AcademyTest.xcodeproj` in Xcode and run with `Cmd+R`. No CLI build — all building and testing is done through Xcode.

### Architecture

MVVM with Swift's `@Observable` macro (iOS 17+). No persistence — state is in-memory only.

**Data flow:**
- `ItemsListViewModel` is the single source of truth, owned by `ContentView` as a `@State` property.
- Views receive the view model via direct parameter passing (not environment), and bind with `@Bindable` where mutation is needed.
- `AddItemViewModel` is ephemeral — owned by `AddItemView` only for the duration of the sheet.

**Navigation:** `ContentView` uses `NavigationSplitView`. Selection is driven by `ItemsListViewModel.selectedItemID` (a `UUID?`). Deletion from the detail view calls `deleteFromDetail(_:)`, which also clears `selectedItemID`.

**Sorting:** `sortedItems` sorts alphabetically (case-insensitive), breaking ties by `creationIndex` (insertion order).

**UI language:** Italian (labels, navigation titles, empty states).

## Android target app

### Build & Run

Open `Kotlin_project/` in Android Studio. All building, running, and previewing is done through Android Studio. There is no test suite.

From the `Kotlin_project/` directory, Gradle CLI commands:
```bash
./gradlew assembleDebug          # build debug APK
./gradlew connectedAndroidTest   # run instrumented tests on device/emulator
./gradlew test                   # run unit tests
```

**SDK config:** `compileSdk = 37`, `targetSdk = 37`, `minSdk = 26` (Android 8.0+).

### Architecture

MVVM with Jetpack Compose. No persistence — state is in-memory only. Package root: `com.example.academytest2026`.

**Source layout:**
```
model/
  Item.kt                    — immutable data class
ui/
  ItemsListViewModel.kt      — single source of truth (ViewModel)
  ItemsListScreen.kt         — LazyColumn list with swipe-to-delete
  AddItemSheet.kt            — ModalBottomSheet for adding items
  ItemRow.kt                 — single list row composable
  FavoriteButton.kt          — reusable star toggle composable
  ItemDetailScreen.kt        — detail view (pending: feature/item-detail-screen)
  ContentScreen.kt           — root screen, owns the ViewModel (pending: feature/navigation)
  theme/                     — generated Material3 theme
```

**Data flow:** `ItemsListViewModel` is instantiated once in `ContentScreen` and passed down as parameters. All mutations flow back up via lambda callbacks — no child composable writes state directly.

**Key constraint:** `Item` is an immutable `data class`. To mutate (e.g. toggle favorite), replace it in the list with `item.copy(...)`. Never mutate fields directly.

### SwiftUI → Compose translation map

| SwiftUI | Compose |
|---|---|
| `@Observable class ItemViewModel` | `data class Item` + `mutableStateListOf` |
| `@Bindable` two-way binding | state hoisting: value down, `onXxx: () -> Unit` lambda up |
| `NavigationSplitView` | `NavigationSuiteScaffold` (adaptive) |
| `.sheet(item:)` | `ModalBottomSheet` gated by a `Boolean` state |
| `List + .onDelete` | `LazyColumn + SwipeToDismissBox` |
| `ContentUnavailableView` | custom empty-state composable |
| `ToolbarItem` | `TopAppBar(actions = { ... })` |
| `@Environment(\.dismiss)` | `onDismiss: () -> Unit` lambda passed from parent |
| `#Preview` | `@Preview(showBackground = true)` |

### Gradle dependencies (already configured)

All declared in `Kotlin_project/gradle/libs.versions.toml` and referenced in `Kotlin_project/app/build.gradle.kts`:
- Compose BOM `2026.02.01` (manages all `androidx.compose.*` versions)
- `material3`, `material3-adaptive`, `material3-adaptive-layout`, `material3-adaptive-navigation`
- `material-icons-core` (required for `Icons.Filled.*` and `Icons.Outlined.*`)
- `lifecycle-viewmodel-compose:2.8.7`
- `navigation-compose:2.8.5`
- `activity-compose`
