import UIKit
import ComponentKt

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
    
        let drawerNode = BindingsKt.buildDrawerComponent()
        
        let windowSizeInfoProvider = IosWindowSizeInfoDispatcher()
        
        let drawerNode2 = BindingsKt.buildAdaptableSizeComponent(
            iosWindowSizeInfoDispatcher: windowSizeInfoProvider
        )
        
        let mainViewController = BindingsKt.ComponentRenderer(
            rootComponent: drawerNode,
            appName: "UiState3 Demo"
        )
    
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
