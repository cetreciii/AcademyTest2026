//
//  ItemsListViewModel.swift
//  AcademyTest
//
//  Created by Giuseppe Travasoni on 06/07/2026.
//

import Foundation

@Observable
final class ItemsListViewModel {
    private(set) var items: [ItemViewModel]
    var selectedItemID: ItemViewModel.ID?

    private var nextCreationIndex: Int

    init(items: [ItemViewModel] = ItemsListViewModel.defaultItems) {
        self.items = items
        nextCreationIndex = (items.map(\.creationIndex).max() ?? 0) + 1
    }

    var sortedItems: [ItemViewModel] {
        items.sorted { first, second in
            let nameComparison = first.name.localizedCaseInsensitiveCompare(second.name)

            if nameComparison == .orderedSame {
                return first.creationIndex < second.creationIndex
            }

            return nameComparison == .orderedAscending
        }
    }

    var selectedItem: ItemViewModel? {
        guard let selectedItemID else { return nil }

        return items.first { $0.id == selectedItemID }
    }

    @discardableResult
    func addItem(named name: String) -> ItemViewModel {
        let trimmedName = name.trimmingCharacters(in: .whitespacesAndNewlines)
        let nextItem = ItemViewModel(
            creationIndex: nextCreationIndex,
            name: trimmedName,
            isFavorite: false
        )

        nextCreationIndex += 1
        items.append(nextItem)

        return nextItem
    }

    func deleteItems(atOffsets offsets: IndexSet) {
        let idsToDelete = offsets.map { sortedItems[$0].id }
        deleteItems(withIDs: Set(idsToDelete))
    }

    func delete(_ item: ItemViewModel) {
        deleteItems(withIDs: [item.id])
    }

    func deleteFromDetail(_ item: ItemViewModel) {
        items.removeAll { $0.id == item.id }
        selectedItemID = nil
    }

    private func deleteItems(withIDs idsToDelete: Set<ItemViewModel.ID>) {
        items.removeAll { idsToDelete.contains($0.id) }

        if let selectedItemID, idsToDelete.contains(selectedItemID) {
            self.selectedItemID = sortedItems.first?.id
        }
    }

    static let defaultItems: [ItemViewModel] = [
        ItemViewModel(creationIndex: 0, name: "Lupo 🐺", isFavorite: true),
        ItemViewModel(creationIndex: 1, name: "Giraffa 🦒", isFavorite: false),
        ItemViewModel(creationIndex: 2, name: "Leone 🦁", isFavorite: false)
    ]
}
