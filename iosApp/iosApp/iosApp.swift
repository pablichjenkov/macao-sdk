import SwiftUI
import ComponentDemoKt

@main
struct iOSDemoApp: App {
    
    let componentToolkitBinder: ComponentToolkitBinder
    let iosBridge: IosBridge
    
    init() {
        self.componentToolkitBinder = ComponentToolkitBinder()
        self.iosBridge = IosBridge(
            platformLifecyclePlugin: componentToolkitBinder.createDefaultPlatformLifecyclePlugin(),
            accountPlugin: componentToolkitBinder.createAccountPluginEmpty()
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ZStack {
                Color.white.ignoresSafeArea(.all) // status bar color
                ContentView(iosBridge: iosBridge)
                    /*.onAppear(perform: {
                        iosBridge.platformLifecyclePlugin.dispatchAppLifecycleEvent(
                            appLifecycleEvent: .start
                        )
                    }).onDisappear {
                        iosBridge.platformLifecyclePlugin.dispatchAppLifecycleEvent(
                            appLifecycleEvent: .stop
                        )
                    }*/
            }.preferredColorScheme(.light)
        }
    }
}
