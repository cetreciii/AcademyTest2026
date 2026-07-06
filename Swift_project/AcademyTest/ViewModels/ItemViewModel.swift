//
//  ItemViewModel.swift
//  AcademyTest
//
//  Created by Giuseppe Travasoni on 06/07/2026.
//

import Foundation

@Observable
final class ItemViewModel: Identifiable, Hashable {
    let id: UUID
    let creationIndex: Int
    var name: String
    var isFavorite: Bool

    init(
        id: UUID = UUID(),
        creationIndex: Int,
        name: String,
        isFavorite: Bool
    ) {
        self.id = id
        self.creationIndex = creationIndex
        self.name = name
        self.isFavorite = isFavorite
    }

    static func == (lhs: ItemViewModel, rhs: ItemViewModel) -> Bool {
        lhs.id == rhs.id
    }

    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
}
