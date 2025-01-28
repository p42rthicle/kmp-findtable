//
//  ListModifier.swift
//  iosApp
//
//  Created by Parth Takkar on 28/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct ListModifier: ViewModifier {

    func body(content: Content) -> some View {
        if #available(iOS 15.0, *) {
            content
                .listRowInsets(.init())
                .listRowSeparator(.hidden)
        } else {
            content
        }
    }
}

extension View {
    func withListModifier() -> some View {
        modifier(ListModifier())
    }
}
