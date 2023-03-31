import UIKit
import AmadeusDemoKt

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    var iosBridgeCopy: ComponentIosBridge?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        
        let safeAreaInsets = UIApplication.shared.windows[0].safeAreaInsets
        print("SafeAreaInsets start:\(safeAreaInsets.left)," +
              "top:\(safeAreaInsets.top)," +
              "end:\(safeAreaInsets.right)," +
              "bottom:\(safeAreaInsets.bottom)"
        )
        let iosBridge = BindingsKt.createPlatformBridge()
        iosBridgeCopy = iosBridge
        
        let left = Int32(safeAreaInsets.left.rounded())
        let top = Int32(safeAreaInsets.top.rounded())
        let right = Int32(safeAreaInsets.right.rounded())
        let bottom = Int32(safeAreaInsets.bottom.rounded())
        
        iosBridge.safeAreaInsets.start = left
        iosBridge.safeAreaInsets.top = top
        iosBridge.safeAreaInsets.end = right
        iosBridge.safeAreaInsets.bottom = bottom
        
        let helloWorldComponent = BindingsKt.buildAmadeusDemoComponent()
        
        let mainViewController = BindingsKt.ComponentRenderer(
            rootComponent: helloWorldComponent,
            iosBridge: iosBridge
        )
        
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
    
    func applicationDidBecomeActive(_ application: UIApplication) {
        iosBridgeCopy?.appLifecycleDispatcher.dispatchAppLifecycleEvent(appLifecycleEvent: .Start)
    }
    
    func applicationDidEnterBackground(_ application: UIApplication) {
        iosBridgeCopy?.appLifecycleDispatcher.dispatchAppLifecycleEvent(appLifecycleEvent: .Stop)
    }
}
