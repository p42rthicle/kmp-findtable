//
//  TimezoneView.swift
//  iosApp
//
//  Created by Parth Takkar on 28/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TimezoneView: View {
    @EnvironmentObject private var timezoneItems: TimezoneItems
    private var timezoneHelper = TimeZoneHelperImpl()
    @State private var currentDate = Date()
    let timer = Timer.publish(every: 1000, on: .main, in: .common).autoconnect()
    @State private var showTimezoneDialog = false
    
    var body: some View {
        NavigationView {
            VStack {
                TimeCard(timezone: timezoneHelper.currentTimeZone(), time: DateFormatter.short.string(from: currentDate), date: DateFormatter.long.string(from: currentDate))
                Spacer()
                List {
                    ForEach(Array(timezoneItems.selectedTimezones), id: \.self) {
                        timezone in
                        NumberTimeCard(timezone: timezone,
                                       time: timezoneHelper.getTime(timezoneId: timezone),
                                       hours: "\(timezoneHelper.hoursFromTimeZone(otherTimeZoneId: timezone))hours from local",
                                       date: timezoneHelper.getDate(timezoneId: timezone))
                        .withListModifier()
                    }
                    .onDelete(perform: deleteItems)
                }
                .listStyle(.plain)
                Spacer()
            }
            .onReceive(timer) { input in
                currentDate = input
            }
            .navigationTitle("World Clocks")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: {
                        showTimezoneDialog = true
                    }) {
                        Image(systemName: "plus")
                            .frame(alignment: .trailing)
                            .foregroundColor(.black)
                    }
                } // Toolbar Item
            } // toolbar
        } // NavigationView
        .fullScreenCover(isPresented: $showTimezoneDialog) {
            TimezoneDialog()
                .environmentObject(timezoneItems)
        }
    }
    
    func deleteItems(at offsets: IndexSet) {
        let timezoneArray = Array(timezoneItems.selectedTimezones)
        for index in offsets {
            let element = timezoneArray[index]
            timezoneItems.selectedTimezones.remove(element)
        }
    }
}

#Preview {
    TimezoneView()
}
