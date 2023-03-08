import UIKit
import HotelBookingKt

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        
        let rootComponent = Main_iosKt.buildAppComponent()
        
        let mainViewController = Main_iosKt.ComponentRenderer(
            rootComponent: rootComponent,
            appName: "Hello World"
        )
        
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
