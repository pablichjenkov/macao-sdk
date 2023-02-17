import UIKit
import shared_hw

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        
        let byteArray = KotlinByteArray.init(size: 10)
        
        let mainViewController = Main_iosKt.ComposeAppViewController(
            appName: "Hello World",
            byteArray: byteArray
        )
        
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
