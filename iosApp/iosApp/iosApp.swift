import UIKit
import ComponentDemoKt

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    
    let iosBridge = IosBridge(
        appLifecycleDispatcher: DefaultAppLifecycleDispatcher()
    )

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
    
        let drawerComponent = BindingsKt.buildDrawerComponent()
        let adaptableComponent = BindingsKt.buildAdaptableSizeComponent()
        let pagerComponent = BindingsKt.buildPagerComponent()
        
        let mainViewController = BindingsKt.ComponentRenderer(
            rootComponent: drawerComponent,
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
        iosBridge.appLifecycleDispatcher.dispatchAppLifecycleEvent(
            appLifecycleEvent: .start
        )
    }
    
    func applicationWillResignActive(_ application: UIApplication) {
        print("applicationWillResignActive")
    }
    
    func applicationDidEnterBackground(_ application: UIApplication) {
        print("applicationDidEnterBackground")
        iosBridge.appLifecycleDispatcher.dispatchAppLifecycleEvent(appLifecycleEvent: .stop)
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        print("applicationWillTerminate")
    }
    
}
