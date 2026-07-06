//
//  FavoriteButton.swift
//  AcademyTest
//
//  Created by Giuseppe Travasoni on 06/07/2026.
//

import SwiftUI

struct FavoriteButton: View {
    @Binding var isFavorite: Bool

    var body: some View {
        Button {
            isFavorite.toggle()
        } label: {
            Image(systemName: isFavorite ? "star.fill" : "star")
                .symbolRenderingMode(.hierarchical)
                .foregroundStyle(isFavorite ? .yellow : .secondary)
                .imageScale(.large)
        }
        .accessibilityLabel(isFavorite ? "Rimuovi dai preferiti" : "Aggiungi ai preferiti")
    }
}

#Preview {
    @Previewable @State var isFavorite = true

    FavoriteButton(isFavorite: $isFavorite)
        .padding()
}
