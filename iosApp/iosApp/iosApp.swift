import SwiftUI
import ComponentDemoKt

@main
struct iOSDemoApp: App {
    
    let componentToolkitBinder: ComponentToolkitBinder
    let iosBridge: IosBridge
    
    init() {
        self.componentToolkitBinder = ComponentToolkitBinder()
        self.iosBridge = IosBridge(
            accountPlugin: componentToolkitBinder.createAccountPluginEmpty()
        )
    }
    
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
