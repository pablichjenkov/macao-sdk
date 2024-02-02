import SwiftUI
import ComponentDemoKt

@main
struct iOSDemoApp: App {
    
    var body: some Scene {
        WindowGroup {
            ZStack {
                Color.white.ignoresSafeArea(.all) // status bar color
                ContentView()
            }
            .preferredColorScheme(.light)
        }
    }
}
