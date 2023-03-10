## Hello Component World
Probably the simplest example of a component. 
<BR>

Bellow code shows how to subclass the Component class to make your own Component.
It is only one function that needs to be implemented.
<BR>

```kotlin
@Composable
override fun Content(modifier: Modifier)
```

<BR>
Here we create our state class to and hosted in the component scope.
<BR>

```kotlin
class HelloWorldComponent : Component() {

    private val helloWorldState = HelloWorldState()

    @Composable
    override fun Content(modifier: Modifier) {
        BackPressHandler(
            component = this,
            onBackPressed = { handleBackPressed() }
        )
        HelloWorldView(helloWorldState)
    }

}

// The way you render it in a Desktop App. You will hand your Component to 
// a DesktopComponentRender Composable function, and it will display it on the screen for you.
fun main() =
    singleWindowApplication(
        title = "Chat",
        state = WindowState(size = DpSize(500.dp, 800.dp))
    ) {
        MaterialTheme {
            DesktopComponentRender(
                rootComponent = HelloWorldComponent(),
                onBackPressEvent = {
                    println("Back pressed event reached root node.")
                    //exitProcess(0) 
                }
            )
        }
    }

// Android Example
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComponentRender(
                rootComponent = HelloWorldComponent(),
                onBackPressEvent = { finishAffinity() }
            )
        }
    }
}

// Web Example
fun main() {
    onWasmReady {
        BrowserViewportWindow("Hello World") {
            MaterialTheme {
                BrowserComponentRender(
                    rootComponent = HelloWorldComponent(),
                    onBackPressEvent = {
                        println("Back pressed event reached root node.")
                    }
                )
            }
        }
    }
}
```

<BR>
Ios code is swift
<BR>

```swift
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
```
