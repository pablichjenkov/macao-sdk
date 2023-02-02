import UIKit
import uistate3

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
    
        let drawerNode = Main_iosKt.buildDrawerNode()
        
        let mainViewController = Main_iosKt.IosNodeRender(
            rootComponent: drawerNode,
            appName: "UiState3 Demo"
        )
    
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
