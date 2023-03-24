import UIKit
import ComponentKt

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
        
        let drawerNode = BindingsKt.buildDrawerComponent()
        let drawerNode2 = BindingsKt.buildAdaptableSizeComponent()
        
        let mainViewController = BindingsKt.ComponentRenderer(
            rootComponent: drawerNode2,
            iosBridge: iosBridge
        )
        
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
    
    func applicationWillEnterForeground(_ application: UIApplication) {
        print("applicationWillEnterForeground")
    }
    
    func applicationDidBecomeActive(_ application: UIApplication) {
        print("applicationDidBecomeActive")
        iosBridgeCopy?.appLifecycleDispatcher.dispatchAppLifecycleEvent(appLifecycleEvent: .Start)
    }
    
    func applicationWillResignActive(_ application: UIApplication) {
        print("applicationWillResignActive")
    }
    
    func applicationDidEnterBackground(_ application: UIApplication) {
        print("applicationDidEnterBackground")
        iosBridgeCopy?.appLifecycleDispatcher.dispatchAppLifecycleEvent(appLifecycleEvent: .Stop)
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        print("applicationWillTerminate")
    }
    
}
