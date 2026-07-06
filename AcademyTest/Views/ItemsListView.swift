//
//  ItemsListView.swift
//  AcademyTest
//
//  Created by Giuseppe Travasoni on 06/07/2026.
//

import SwiftUI

private struct AddItemRequest: Identifiable {
    let id = UUID()
}

struct ItemsListView: View {
    @Bindable var viewModel: ItemsListViewModel
    @State private var addItemRequest: AddItemRequest?

    var body: some View {
        content
        .navigationTitle("Oggetti")
        .toolbar {
            ToolbarItem(placement: .topBarTrailing) {
                Button {
                    addItemRequest = AddItemRequest()
                } label: {
                    Image(systemName: "plus")
                }
                .accessibilityLabel("Aggiungi oggetto")
            }
        }
        .sheet(item: $addItemRequest) { _ in
            AddItemView { name in
                viewModel.addItem(named: name)
            }
        }
    }

    @ViewBuilder
    private var content: some View {
        if viewModel.items.isEmpty {
            ContentUnavailableView {
                Label("Nessun oggetto", systemImage: "tray")
            } description: {
                Text("Aggiungi un oggetto dalla barra in alto.")
            } actions: {
                Button("Aggiungi oggetto") {
                    addItemRequest = AddItemRequest()
                }
            }
        } else {
            List(selection: $viewModel.selectedItemID) {
                ForEach(viewModel.sortedItems) { item in
                    NavigationLink(value: item.id) {
                        ItemRowView(item: item)
                    }
                }
                .onDelete { offsets in
                    viewModel.deleteItems(atOffsets: offsets)
                }
            }
        }
    }
}

#Preview {
    NavigationSplitView {
        ItemsListView(viewModel: ItemsListViewModel())
    } detail: {
        Text("Dettaglio")
    }
}
