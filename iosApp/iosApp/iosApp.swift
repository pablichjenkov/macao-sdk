import SwiftUI
import ComponentDemoKt

@main
struct iOSDemoApp: App {

    let iosBridge = IosBridge(
        platformLifecyclePlugin: BindingsKt.createDefaultPlatformLifecyclePlugin()
    )

    var body: some Scene {
       WindowGroup {
           ZStack {
               Color.white.ignoresSafeArea(.all) // status bar color
               ContentView(iosBridge: iosBridge)
                   .onReceive(NotificationCenter.default.publisher(for: UIApplication.willEnterForegroundNotification)) { _ in
                       print("application_willEnterForeground")
                   }
                   .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)) { _ in
                       print("application_didBecomeActive")
                       iosBridge.platformLifecyclePlugin.dispatchAppLifecycleEvent(
                           appLifecycleEvent: .start
                       )
                   }
                   .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification)) { _ in
                       print("application_willResignActive")
                   }.onReceive(NotificationCenter.default.publisher(for: UIApplication.didEnterBackgroundNotification)) { _ in
                       print("application_didEnterBackground")
                       iosBridge.platformLifecyclePlugin.dispatchAppLifecycleEvent(
                           appLifecycleEvent: .stop
                       )
                   }
           }.preferredColorScheme(.light)

       }
        
   }

}
