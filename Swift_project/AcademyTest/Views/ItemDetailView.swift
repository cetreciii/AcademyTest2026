//
//  ItemDetailView.swift
//  AcademyTest
//
//  Created by Giuseppe Travasoni on 06/07/2026.
//

import SwiftUI

struct ItemDetailView: View {
    @Bindable var item: ItemViewModel
    let onDelete: () -> Void

    var body: some View {
        Form {
            Section("Oggetto") {
                LabeledContent("Nome", value: item.name)

                HStack {
                    Text("Preferito")
                    Spacer()
                    FavoriteButton(isFavorite: $item.isFavorite)
                }
            }
        }
        .navigationTitle(item.name)
        .toolbar {
            ToolbarItem(placement: .topBarTrailing) {
                Button(role: .destructive, action: onDelete) {
                    Image(systemName: "trash")
                }
                .accessibilityLabel("Elimina oggetto")
            }
        }
    }
}

#Preview {
    NavigationStack {
        ItemDetailView(
            item: ItemViewModel(
                creationIndex: 0,
                name: "Álgebra",
                isFavorite: false
            )
        ) {}
    }
}
