import UIKit
import iosAppKt

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
    
        let drawerNode = Main_iosKt.buildDrawerComponent()
        
        let windowSizeInfoProvider = IosWindowSizeInfoDispatcher()
        
        let drawerNode2 = Main_iosKt.buildAdaptableSizeComponent(
            iosWindowSizeInfoDispatcher: windowSizeInfoProvider
        )
        
        let mainViewController = Main_iosKt.ComponentRenderer(
            rootComponent: drawerNode,
            appName: "UiState3 Demo"
        )
    
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
