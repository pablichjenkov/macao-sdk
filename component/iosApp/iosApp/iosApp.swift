import UIKit
import ComponentKt

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
    
        let safeAreaInsets = UIApplication.shared.windows[0].safeAreaInsets
        print("SafeAreaInsets start:\(safeAreaInsets.left)," +
              "top:\(safeAreaInsets.top)," +
              "end:\(safeAreaInsets.right)," +
              "bottom:\(safeAreaInsets.bottom)"
        )
        let platformDelegate = BindingsKt.createPlatformDelegate()
        
        let left = Int32(safeAreaInsets.left.rounded())
        let top = Int32(safeAreaInsets.top.rounded())
        let right = Int32(safeAreaInsets.right.rounded())
        let bottom = Int32(safeAreaInsets.bottom.rounded())
        
        platformDelegate.safeAreaInsets.start = left
        platformDelegate.safeAreaInsets.top = top
        platformDelegate.safeAreaInsets.end = right
        platformDelegate.safeAreaInsets.bottom = bottom
        
        let drawerNode = BindingsKt.buildDrawerComponent()
        let drawerNode2 = BindingsKt.buildAdaptableSizeComponent()
        
        let mainViewController = BindingsKt.ComponentRenderer(
            rootComponent: drawerNode2,
            platformDelegate: platformDelegate
        )
        
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
