//
//  Utils.swift
//  iosApp
//
//  Created by Parth Takkar on 28/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation

extension DateFormatter {
    static let short: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .none
        formatter.timeStyle = .short
        return formatter
    }()
    static let long: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .long
        formatter.timeStyle = .none
        return formatter
    }()
}
