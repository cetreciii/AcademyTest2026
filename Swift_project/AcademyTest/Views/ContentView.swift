//
//  ContentView.swift
//  AcademyTest
//
//  Created by Giuseppe Travasoni on 06/07/2026.
//

import SwiftUI

struct ContentView: View {
    @State private var viewModel = ItemsListViewModel()

    var body: some View {
        NavigationSplitView {
            ItemsListView(viewModel: viewModel)
        } detail: {
            if let selectedItem = viewModel.selectedItem {
                ItemDetailView(item: selectedItem) {
                    viewModel.deleteFromDetail(selectedItem)
                }
            } else {
                ContentUnavailableView(
                    "Nessun oggetto",
                    systemImage: "tray",
                    description: Text("Aggiungi un oggetto dalla lista.")
                )
            }
        }
    }
}

#Preview {
    ContentView()
}
