//
//  CardModifier.swift
//  iosApp
//
//  Created by Parth Takkar on 28/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct CardModifier: ViewModifier {
  func body(content: Content) -> some View {
    content
      .cornerRadius(16)
      .shadow(color: Color.black.opacity(0.2), radius: 16, x: 0, y: 0)
  }
}
