//
//  ItemRowView.swift
//  AcademyTest
//
//  Created by Giuseppe Travasoni on 06/07/2026.
//

import SwiftUI

struct ItemRowView: View {
    @Bindable var item: ItemViewModel

    var body: some View {
        HStack(spacing: 12) {
            VStack(alignment: .leading, spacing: 4) {
                Text(item.name)
                    .font(.headline)

                Text(item.isFavorite ? "Preferito" : "Non preferito")
                    .font(.caption)
                    .foregroundStyle(.secondary)
            }

            Spacer()

            FavoriteButton(isFavorite: $item.isFavorite)
                .buttonStyle(.borderless)
        }
        .padding(.vertical, 4)
    }
}

#Preview {
    List {
        ItemRowView(
            item: ItemViewModel(
                creationIndex: 0,
                name: "Caffe",
                isFavorite: true
            )
        )

        ItemRowView(
            item: ItemViewModel(
                creationIndex: 1,
                name: "Zaino",
                isFavorite: false
            )
        )
    }
}
