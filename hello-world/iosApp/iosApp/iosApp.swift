import UIKit
import HelloWorldKt

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        
        let helloWorldComponent = Main_iosKt.buildHelloWorldComponent()
        
        let mainViewController = Main_iosKt.ComponentRenderer(
            rootComponent: helloWorldComponent,
            appName: "Hello World"
        )
        
        window?.rootViewController = mainViewController
        window?.makeKeyAndVisible()
        return true
    }
}
