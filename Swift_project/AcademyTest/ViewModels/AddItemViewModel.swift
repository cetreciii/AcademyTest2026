//
//  AddItemViewModel.swift
//  AcademyTest
//
//  Created by Giuseppe Travasoni on 06/07/2026.
//

import Foundation

@Observable
final class AddItemViewModel {
    var name = ""

    var trimmedName: String {
        name.trimmingCharacters(in: .whitespacesAndNewlines)
    }

    var canSave: Bool {
        !trimmedName.isEmpty
    }
}
