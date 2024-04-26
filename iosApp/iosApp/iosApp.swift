import SwiftUI
import MacaoSdkDemoKt

@main
struct iOSDemoApp: App {
    
    let iosBridge = MacaoSdkDemoKt.createPlatformBridge()
    
    var body: some Scene {
        WindowGroup {
            ZStack {
                Color.white.ignoresSafeArea(.all) // status bar color
                ContentView(iosBridge: iosBridge)
            }
            .preferredColorScheme(.light)
        }
    }
}
