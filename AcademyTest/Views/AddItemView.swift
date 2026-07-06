//
//  AddItemView.swift
//  AcademyTest
//
//  Created by Giuseppe Travasoni on 06/07/2026.
//

import SwiftUI

struct AddItemView: View {
    @Environment(\.dismiss) private var dismiss
    @State private var viewModel = AddItemViewModel()
    @FocusState private var isNameFieldFocused: Bool

    let onSave: (String) -> Void

    var body: some View {
        NavigationStack {
            Form {
                Section("Nuovo oggetto") {
                    TextField("Nome", text: $viewModel.name)
                        .textInputAutocapitalization(.sentences)
                        .submitLabel(.done)
                        .focused($isNameFieldFocused)
                        .onSubmit(save)
                }
            }
            .navigationTitle("Aggiungi")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .cancellationAction) {
                    Button("Annulla") {
                        dismiss()
                    }
                }

                ToolbarItem(placement: .confirmationAction) {
                    Button("Salva", action: save)
                        .disabled(!viewModel.canSave)
                }
            }
        }
        .presentationDetents([.medium])
        .onAppear {
            isNameFieldFocused = true
        }
    }

    private func save() {
        guard viewModel.canSave else {
            return
        }

        onSave(viewModel.trimmedName)
        dismiss()
    }
}

#Preview {
    AddItemView { _ in }
}
