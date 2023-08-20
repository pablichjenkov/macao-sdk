## Component Toolkit
Component-toolkit is basically a state management library. At its core, a Component is nothing else 
than a State Holder class to help with state hoisting. 
In the context of this library, Components are state holders that form a tree, in this tree, 
Components can interact with each other directly or indirectly. In a direct manner, a parent 
component will create or hoist children components so it knows the children types and functionality.
In an indirect way, a Component can connect to another Component in the tree that is not its 
children. Using Component deep linking, any Component in the tree can connect to another Component 
and send requests and receive results back.

#### Guides
1. [Simple Component](#simple-component)
2. [Navigation Component](#navigation-component)
3. [Platform Renderers](#platform-renderers)
4. [Component Deep Linking](#components-deep-linking)
5. [Component Extensions](#components-ext)

#### <a id="simple-component"></a>Simple Component
To create a component all you need to do is extend the Component class and provide an implementation
for the Composable content.

```kotlin
  @Composable 
  fun Content() {
  }
```
A simple Component class will look like this.

```kotlin
class SimpleComponent(
    val screenName: String,
    val onResult: (result: Boolean) -> Unit
) : Component() {

    override fun onStart() {
        println("${instanceId()}::onStart()")
    }

    override fun onStop() {
        println("${instanceId()}::onStop()")
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("${instanceId()}::Composing()")
        // To handle back press events.
        consumeBackPressEvent()
        Column (
            modifier = modifier.fillMaxSize()
                .background(bgColor)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = screenName)
            Button(
                modifier = Modifier.padding(vertical = 40.dp),
                onClick = { onResult(true) }
            ) { 
                Text(text = "Click me") 
            }
        }
    }
}
```

#### <a id="navigation-component"></a>Navigation Component
Simple Components are basically the leaf nodes in the Component tree. To build the actual tree we need Components that can hoist children Components. The toolkit defines an interface just for that, `ComponentWithChildren`.
This interface is the base to `NavigationComponent` interface which is implemented by many navigator Components. The next code snippet shows how to instantiate a NavBarComponent.

```kotlin
fun build(): NavBarComponent<NavBarStatePresenterDefault> {

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Home",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent(
                    "Orders",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent(
                    "Settings",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            )
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            config = NavBarComponent.DefaultConfig,
            content = NavBarComponent.DefaultNavBarComponentView
        )
        navBarComponent.setNavItems(navbarNavItems, 0)
        return navBarComponent
    }
```

Above code snippet will produce bellow image. 

<image src="https://github.com/pablichjenkov/component-toolkit/assets/5303301/d331f0a9-1241-484a-82bf-517f3fdd3168" width=220>

There are many other navigation Components such as `DrawerComponent`, `PagerComponent`, `PanelComponent`, `TopBarComponent`, `StackComponent` and a special type of Component named `AdaptiveSizeComponent` which takes 3 NavigationComponents as children, each one to be used depending on the screen size when it varies.
You can also create your own navigator by implementing the NavigationComponent interface. Check the code in NavBarComponent for instance, to have some guidance on how to do so.

#### <a id="platform-renderers"></a>Platform Renderes
Once you create a Component or more often a NavigationComponent, we need to render its Composable content. For that the toolkit provides a Component renderer Composable per each platform.
```kotlin
// Android
AndroidComponentRender(
    rootComponent: rootComponent,
    onBackPress: () -> Unit = {}
)

// iOS
fun IosComponentRender(
    rootComponent: Component,
    iosBridge: IosBridge,
    onBackPress: () -> Unit = {}
)

// Desktop
@Composable
fun DesktopComponentRender(
    rootComponent: Component,
    windowState: WindowState,
    desktopBridge: DesktopBridge,
    onBackPress: () -> Unit = {}
)

// JS
@Composable
fun BrowserComponentRender(
    rootComponent: Component,
    onBackPress: () -> Unit = {}
)
```
This is a snippet on Android, see the toolkit Demo App for the other platforms.
```kotlin
class DrawerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = DrawerTreeBuilder.build()
        setContent {
            MaterialTheme {
                AndroidComponentRender(
                    rootComponent = rootComponent,
                    onBackPress = { finish() }
                )
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }

}
```

#### <a id="components-deep-linking"></a>Components Deep Linking
A parent Component can activate/deactivate or show/hide a direct child Component. It does so by calling dispatchStart()/dispatchStop() on the respective child. But sometimes in an App, a user needs to go to another screen that is not a direct child of the current screen. Let's say there is a banner in the Feed screen that takes users to the Setting screen to see their most recent credit score changes. 
The toolkit support this type of navigation by using deep links. Each Component has a property named `uriFragment` that represents a path in the deep link uri. The App developer most know the path to the Component to be able to navigate to it.
```kotlin

val rootComponent = LocalRootComponentProvider.current

Button(
    modifier = Modifier.padding(vertical = 40.dp),
    onClick = {
        rootComponent?.navigateToDeepLink(
            DeepLinkMsg(
                path = listOf("_navigator_adaptive", "*", "Settings", "Page 3"),
                resultListener = { result, component ->
                    println("Deep link result: $result")
                    
                    val responseComponent = component as? SimpleResponseComponent
                    
                    if (responseComponent == null) {
                        println("Cast to SimpleResponseComponent failed")
                        return
                    }
                    coroutineScope.launch {
                        responseComponent.resultSharedFlow.collect {
                            println("Receiving response = $it")
                        }
                    }
                }
            )
        )
    }
) {
    Text(text = "Go To Settings/Page 3")
}
```
Lets decode above snippet:
1. First it grabs the `rootComponent` from a CompositionLocal.
2. Create a `DeepLinkMsg` specifying the `path` and a `resultListener` to receive the result of the deep link navigation as well as the navigated to Component.
`path = listOf("_navigator_adaptive", "*", "Settings", "Page 3")` indicates that the first Component which `uriFragmet = _navigator_adaptive`, then `*` means that there is a NavigationComponent in the deep link route that will accept any uri path as name. Then it goes through the `Settings` TopBarComponent and then finally to `Page 3` SimpleResponseComponent.
3. Call Component::navigateToDeepLink(DeepLinkMsg) function to start the navigation. The algorithm will traverse the Component tree matching the uri path against the Components uriFragment property. As it traverse the tree it activates the Components that match each uri path. If a full uri match succeed, then a `DeepLinkResult.Success` is returned and as a result of traversing the tree, the Component represented by the last uri path will be the one active.
If no child Component is found to match a specific uri path at a specific tree level, then a `DeepLinkResult.Error` will be returned.
4. Once the component has been navigated to, the DeepLinkResult gives a reference to it. If you know the type then cast it and start interacting with it. That is the code below:
    ```kotlin
      val responseComponent = component as? SimpleResponseComponent
      if (responseComponent == null) {
            println("Cast to SimpleResponseComponent failed")
            return
        }
        coroutineScope.launch {
            responseComponent.resultSharedFlow.collect {
                println("Receiving response = $it")
            }
        }
    ```

#### <a id="components-ext"></a>Component Extensions
The toolkit provides some nice extensions to match some extension functions popular in Android.
```kotlin
suspend fun Component.repeatOnLifecycle(
    block: suspend CoroutineScope.() -> Unit
)
```

```kotlin
@Composable
fun <T> Flow<T>.collectAsStateWithLifecycle(
    initialValue: T,
    component: Component,
    context: CoroutineContext = EmptyCoroutineContext
): State<T>
```

```kotlin
@Composable
fun <T> StateFlow<T>.collectAsStateWithLifecycle(
    component: Component,
    context: CoroutineContext = EmptyCoroutineContext
): State<T>
```

In adition to above extension functions the toolkit provides a `ViewModel` class for those familiar with that architecture. To use the ViewModel in this library you have to use the `ViewModelComponent` class defined as bellow.
```kotlin
open class ViewModelComponent<VM : ViewModel>(
    private var viewModel: VM,
    private val content: @Composable (VM) -> Unit
) : Component()
```
Sample usage:
```kotlin
val SimpleViewModelView: @Composable (SimpleViewModel) -> Unit = { viewModel ->
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = viewModel.text,
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = {
                viewModel.next()
            }
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
    
private val myViewModel = SimpleViewModel(
    screenName = "$screenName/Page 2",
    bgColor = Color.Green,
    onNext = {
        backStack.push(Step3)
    }
)

val myComponent = ViewModelComponent(
    viewModel = myViewModel,
    content = SimpleViewModelView
)

// Somewhere in the parent Component call
myComponent.dispatchStart()
```
