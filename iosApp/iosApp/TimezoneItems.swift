//
//  TimezoneItems.swift
//  iosApp
//
//  Created by Parth Takkar on 28/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

class TimezoneItems: ObservableObject {
  @Published var timezones: [String] = []
  @Published var selectedTimezones = Set<String>()

  init() {
      self.timezones = TimeZoneHelperImpl().getTimeZoneStrings()
  }
}
