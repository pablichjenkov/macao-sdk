## Component Toolkit
Component-toolkit is a state management library rather than a component ui library. A Component is nothing else than a State Holder class to help with state hoisting.
In the context of this library, Components are state holders that form a tree, in this tree,
Components can interact with each other directly or indirectly.
In a direct manner, a parent component will create or hoist children components so it knows the children types and functionality.
In an indirect way, a Component can connect to another Component in the tree that is not necesarelly its children. Using Component deep linking, any Component in the tree can connect to another Component and send requests and receive results back.

#### Guides (As of v0.5.9)
1. [Simple Component](#simple-component)
2. [State Component](#state-component)
3. [Navigation Component](#navigation-component)
4. [Platform Renderers](#platform-renderers)
5. [Component Deep Linking](#components-deep-linking)
6. [Component Extensions](#components-ext)

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

#### <a id="state-component"></a>State Component
A simple component like the one in the previous section offers a lot of possibilities but with freedom comes responsibility and we tend to get creative and invent so many things that are fine 
for us but hard to understand for others. So, to help with putting a little bit of order, the library has a couple of built-in components that follow the MVVM pattern which is really popular. All
predefined components are design using the MVVM and the library encourages other developers creating components to do the same.

Talking about extending functionality, the only Component that can be inherited from, is the abstract `Component` class. The rest of the components, extend functionality by using a parametrized 
`ViewModel` that gets passed in the component constructor. Actually not, is a factory of this `ViewModel` what gets passed to the Component constructor. The library discourage inheritance and 
encourage composition, you should to write your custom components the same way. No inheritance other than the `Component class`.

Bellow snippet shows a custom ViewModel class `DemoViewModel` and its corresponding factory.

```kotlin
class DemoViewModel(
    private val component: StateComponent<DemoViewModel>,
    viewModelDependency: ViewModelDependency
) : ComponentViewModel() {

    val name = "DemoViewModel"

    override fun onStart() {
        println("My bound Component ID = ${component.instanceId()}")
        viewModelDependency.getX()
        ...
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }
}

class DemoViewModelFactory(
    private val viewModelDependency: ViewModelDependency, // Prefereably inject it using a DI library
) : ComponentViewModelFactory<DemoViewModel> {

    override fun create(component: StateComponent<DemoViewModel>): DemoViewModel {
        return DemoViewModel(component, viewModelDependency)
    }
}
```

And bellow snippet shows how to create the StateComponent instance parameterized to this DemoViewModel class.

```kotlin
// You can have a reference to a composable lambda that is an extention of StateComponent<DemoViewModel> in another file.
// file: DemoComponentView.kt

val DemoComponentView: @Composable StateComponent<DemoViewModel>.(
        modifier: Modifier,
        componentViewModel: DemoViewModel
    ) -> Unit = { modifier, demoViewModel ->
        Text("My bound Component ID = ${instanceId()}")
    }

// file: DemoComponentTest.kt

fun test1() {
    val component1 = StateComponent<DemoViewModel>(
        viewModelFactory = DemoViewModelFactory(ViewModelDependency()),
        content = DemoComponentView
    )
    
    component1.dispatchStart()
}
```

Or define the composable function in the call-site as a trailing lambda

```kotlin
fun test2() {
    val component2 = StateComponent<DemoViewModel>(
        viewModelFactory = DemoViewModelFactory(ViewModelDependency())
    ) { modifier: Modifier, componentViewModel: DemoViewModel ->
        
        Text("This Composable is an extension function of StateComponent<DemoViewModel>")
        Text("My bound Component ID = ${instanceId()}")
        Text("My componentViewModel = ${componentViewModel.name}")
    }

    component2.dispatchStart()
}
```

Above API design gives a developer enough freedom to define whatever ViewModel class and whatever Composable function to render that specific ViewModel type. Thanks to Kotlin well design generics api.

#### <a id="navigation-component"></a>Navigation Component
Simple components or StateComponents are basically leaf nodes in the Component tree. They are made just for that, rendering a given state, but that is it.

To make a full App you will need more sophisticated components. Components that allow to do things like navigation between components, data passing between components and things of this nature. 
These components will form the actual component tree. A fundamental aspect of a node in a tree is to have children, so we need components that can hoist children components. The toolkit defines an 
interface just for that, `ComponentWithChildren`.

`ComponentWithChildren` interface is the base to `NavigationComponent` interface which is implemented by many navigator Components. Most of the time you wont be implementing 
`ComponentWithChildren` but `NavigationComponent` interface. The library will try to include the more popular navigation components anyways so you don't have to create a navigation component but 
just styling the existing ones to your needs.

The next code snippet shows how to instantiate a BottomNavigationComponent.

```kotlin
fun testBottomNavigationComponentCreation() {
    val bottomNavigationComponent = BottomNavigationComponent(
            viewModelFactory = BottomNavigationDemoViewModelFactory(
                BottomNavigationComponentDefaults.createBottomNavigationStatePresenter()
            ),
            content = BottomNavigationComponentDefaults.BottomNavigationComponentView
        )
        
    bottomNavigationComponent.dispatchStart()
}
```

Above code snippet will produce a component similar to the image bellow.

<image src="https://github.com/pablichjenkov/component-toolkit/assets/5303301/d331f0a9-1241-484a-82bf-517f3fdd3168" width=220 />

Previous code snippet uses `BottomNavigationComponentDefaults.BottomNavigationComponentView` which is a Composable function that renders classes of `BottomNavigationDemoViewModel` type. But if you 
want a custom rendering of your `BottomNavigationCustomViewModel` you just need to define your own and pass it to the component.

`A custom BottomNavigation composable`
```kotlin
val CustomBottomNavigationView: @Composable BottomNavigationComponent<BottomNavigationCustomViewModel>.(
        modifier: Modifier,
        childComponent: Component // Currently selected component child in the BottomNavigation
    ) -> Unit = { modifier, childComponent ->
        NavigationBottom(
            modifier = modifier,
            navbarStatePresenter = navBarStatePresenter
        ) {
           Column {
              Text("My Custom rendering of BottomNavigationCustomViewModel")
              childComponent.Content(Modifier)
           }
        }
    }
```

`A custom BottomNavigation ViewModel`
```kotlin
class BottomNavigationDemoViewModel(
    bottomNavigationComponent: BottomNavigationComponent<BottomNavigationDemoViewModel>,
    override val bottomNavigationStatePresenter: BottomNavigationStatePresenterDefault,
    private val httpClient: HttpClient()
) : BottomNavigationComponentViewModel(bottomNavigationComponent) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        coroutineScope.launch {
            val navBarItems = fetchNavBarItems()
            val selectedIndex = 0
            bottomNavigationComponent.setNavItems(navBarItems, selectedIndex)
        }
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    private suspend fun fetchNavBarItems(): MutableList<NavItem> {
        return httpClient.fetchRemoteNavItems()
    }

}
```

There are many other navigation Components such as `DrawerComponent`, `PagerComponent`, `PanelComponent`, `TopBarComponent`, `StackComponent` and a special type of Component named `AdaptiveSizeComponent` which takes 3 NavigationComponents as children, each one to be used depending on the screen size when it varies.
You can also create your own navigator by implementing the NavigationComponent interface. Check the code in NavBarComponent for instance, to have some guidance on how to do so.

#### <a id="platform-renderers"></a>Platform Renderes
You may have noticed in previous code samples the use of `component.dispatchStart()` function. This function basically tells the component that the underlying platform lifecycle gave the signal to start. In the case of Android it is an Activity in the case of iOS a UiViewController, the web uses the window browser lifecycle and desktop the desktop window lifecycle too. In reality, you don't need to deal with platform's lifecycle and having to call `component.dispatchStart()` or `component.dispatchStop()`. Neither you need to call `Component::Content(Modifier)` in each platform to render the component content, the toolkit has that functionality already built in. The toolkit provides a Component renderer Composable function in each platform, that just receive the root component and takes care of the rest. See bellow code snippets for more details.

```kotlin
// Android
@Composable
fun AndroidComponentRender(
   rootComponent: Component,
   androidBridge: AndroidBridge,
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
   jsBridge: JsBridge,
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

##### repeatOnLifecycle
```kotlin
suspend fun Component.repeatOnLifecycle(
    block: suspend CoroutineScope.() -> Unit
)
```

##### collectAsStateWithLifecycle
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

##### MVVM architecture
In adition to above extension functions, the toolkit provides a `abstract ComponentViewModel` class for those familiar with MVVM architecture. To use this specialized ViewModel, you have to use the `StateComponent<VM : ComponentViewModel>` class. See the StateComponent section above [State Component](#state-component).
