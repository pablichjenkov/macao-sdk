import UIKit
import ComponentKt

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
    
        let drawerNode = BindingsKt.buildDrawerComponent()
        let drawerNode2 = BindingsKt.buildAdaptableSizeComponent()
        
        let mainViewController = BindingsKt.ComponentRenderer(
            rootComponent: drawerNode2
        )
    
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
