## UI-State3
Yet another State Management option in ***Jetpack/Jetbrains Compose***. The original
concept is based on the **Coordinators Architecture** pattern (popular within iOS folks) and also
considering the latest trends in Android UI state management(Decompose/Voyager/Appyx). The motive behind the library is to
create a tool that allows to build Apps quickly, with a variaty of navigation options and seamless
effort to achieve scalability.
<BR>
The library separates UI state from any of the underlying platforms, could be Desktop mobile doesn't
matter. The library itself contain a mechanism for navigation but it can be integrated with other
navigation solutions out there.

<H4>Getting Started</H4>
The best way to get familiar with the concepts is to check the project wiki [wiki](https://github.com/pablichjenkov/uistate3/wiki)

<H4>Show me some code</H4>

```kotlin
// An example of how to make a tree. In this case a DrawerNode that will have a BottomBarNode as one of its child.
object DrawerTreeBuilder {

    private lateinit var DrawerNode: DrawerNode

    fun build(): DrawerNode {

        if (DrawerTreeBuilder::DrawerNode.isInitialized) {
            return DrawerNode
        }

        val DrawerNode = DrawerNode()

        val drawerNavItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = TopBarNode("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                node = buildNavBarNode(),
                selected = false
            ),
            NodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = TopBarNode("Settings", Icons.Filled.Email) {},
                selected = false
            )
        )

        return DrawerNode.also { it.setItems(drawerNavItems, 0) }
    }

    private fun buildNavBarNode(): NavBarNode {

        val NavBarNode = NavBarNode()

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Active",
                icon = Icons.Filled.Home,
                node = TopBarNode("Orders/Active", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                node = TopBarNode("Orders/Past", Icons.Filled.Settings) {},
                selected = false
            ),
            NodeItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                node = TopBarNode("Orders/New Order", Icons.Filled.Add) {},
                selected = false
            )
        )

        return NavBarNode.also { it.setItems(navbarNavItems, 0) }
    }

}

// Once you have a tree, then just call Content(Modifier) on the root Node. The compose 
// machinery will traverse the tree painting on the screen each active Node.Content(Modifier).

fun main() = application {

    val DrawerNode: DrawerNode = remember(key1 = this) {
        DrawerTreeBuilder.build()
    }

    DrawerNode.Content(Modifier)
}
```

Above code will produce the desktop application shown in the video below. The **DesktopAppNode** is
a demo node that consists of, an **AdaptableSizeNode** as a parent of 3 **NavigatorNodes**(Drawer,
BottomBar, Panel) that share children of type **BackStackNodes** which contain single page Nodes(
Page1, Page2, Page3). When the window size changes, the **DesktopAppNode** sets the corresponding **
NavigatorNode** as active, and the children nodes are tranfered from one navigator parent to the new
active navigator parent. In the video there is also a demonstration of how deep links work. Deep
links are represented as a path in the state tree, each subpath represents a node. When a deep link
path is traversed, each node represented in a subpath is activated, all the way upto the last node.
The node path can be mapped to a web url.

https://user-images.githubusercontent.com/5303301/209282619-06748ddc-3fb1-4a74-8849-0c2215bbafc4.mp4

Lets see how the Android code will look like for the same NavigatonDrawer type of Application.

```kotlin
class DrawerActivity : ComponentActivity() {

    private val stateTreeHolder by viewModels<DrawerStateTreeHolder>()
    private lateinit var StateTree: Node

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root node is a NavigationDrawer
        StateTree = stateTreeHolder.getOrCreate().apply {
            context.rootNodeBackPressedDelegate = ForwardBackPressCallback { finish() }
        }

        setContent {
            MaterialTheme {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides AndroidBackPressDispatcher(this@DrawerActivity),
                ) {
                    StateTree.Content(Modifier)
                }
            }
        }
    }

}
```

https://user-images.githubusercontent.com/5303301/205950623-1944bd2c-52c6-4bda-80a0-a7408055e1e1.mp4

<H4>Navigation State Preserved</H4>

Notice how **Navigation State** is preserved in each screen as the user move through different parts
of the App. They can also have a **Back Button Press** party and the original navigation path is
preserved. No forced jumps to graph startDestinations or sort of things.

<H4>Navigation Nodes Nesting</H4>

In the case of large screens you can have multiple Navigation nodes in a split screen or you could
nest them within another Navigation node. In the next video, one of the screens is splitted in two
sections each with a navivation drawer on its own. Observe than switching pages in the outer
NavigationDrawer doesn't affect the state of those inner NavigationDrawers. This is possible because
each parent node contains its own navigation state and is not affected by being switched/swapped by
a sibling node.

https://user-images.githubusercontent.com/5303301/208034085-7a8cf47c-3339-45b0-b411-b94be1f5f974.mov

or in the device, although is not to practical in this case.

https://user-images.githubusercontent.com/5303301/205610976-ce7e3006-bbdf-4f42-941c-de784714b6cd.mp4

<H4>Adaptable UI</H4>

A NavigatorNode can be replaced with another NavigatorNode at any point in time while the App is
running. All you have to do is transfer children nodes from one NavigatorNode to the other and
attach the NavigatorNode to the parent Node. Automatically the tree will refresh its content. See
AdaptableWindowNode for an example.

https://user-images.githubusercontent.com/5303301/206708221-a8d13577-f38d-4b07-bcbf-f66cbca26d46.mov

The corresponding scenario in a phone

https://user-images.githubusercontent.com/5303301/206601512-84ff3d70-28e8-4cb3-bbf6-67f134f6fe08.mov

Above videos show how the App switches between a **DrawerNode** and a **PanelNode**, but it could be
any Node that implements NavigatorNode interface. Eg: NavBarNode, PagerNode, YourCustomNavigatorNode
etc

<H3>Library Concepts</H3>

The pattern consists of designing your App navigation as if it was a Tree data structure. Users can
visit the different nodes of the tree at any desired time. When the user modifies a node and visit a
different node, the node state remains as the user left it. The next time the user comes back to
that node, it will have the same state when it was last visited.

<H4>Node</H4>

A **Node** is the class used to build the State Tree.

Each ***Node*** is in charge of hadling **back press** events as well as Activity Lifecycle events
like start and stop. Also each ***Node*** is responsible for propagating Start/Stop events to its
children nodes. In the case of a back pressed events, the top most node will have the opportunity to
first process the event. In case no processing is needed, it has the responsability to pass the
event up to its parent node. Then the parent do the same until the event reaches the root node.

An example of a ***Node*** implementation that handles its children as a back stack is the ***
BackStackNode***. It will have only one Active child at any time and this child will ocuppy the
entire Viewport assigned to the ***BackStackNode***. The pattern allows to build any type of nested
navigation within the tree. It is a matter of just placing a ***Node***
implementation in the tree and handle the children nodes whatever the way you want.

The ***Node*** abstract class produces a Composable content representing its State every time
recomposition happens.

```kotlin
    @Composable
abstract fun Content(modifier: Modifier)
```

The state tree is not affected by recomposition or lifecycle changes. It can live in the
ActivityRetained scope or in the Application scope and must survive configuration changes. It is up
to the user of the State Tree where to scope it. When the root Composable is recreated in case of
screen size, layout or rotation changes. The Tree will traverse all the children and will recreate
the previous Composable output before the configuration changes.

<H4>NodeContext</H4>

**NodeContext** is used to share fundamental elements within the nodes in the state tree, similar
to **CompositionLocal** in the Composable tree. The NodeContext is used to dispatch the BackPressed
event from child node to parent node. It also can be queried to fetch the closest parent node
handling navigation, things of that nature. This class should not be used to share data between the
nodes, passing data implicitly within the tree is discouraged. Implicit data passing decreases
portability of the node, it limits to use the **Node** in another application that might not provide
the implicit state it depends on. To pass data to *Nodes* use the constructor, is the better option
for testability as well.

<H3>Build the Tree</H3>
Creating a tree of nodes is simple. Start by the root node and append child nodes to it. Some nodes
may have already a predifined child types that it knows how to handle. Bellow is an example of how
to build a bottom bar navigator node. Check the other examples.

```kotlin
        val NavBarNode = NavBarNode(rootParentNodeContext)

val PagerNode = PagerNode(NavBarNode.context)

val pagerNavItems = mutableListOf(
    NavigationNodeItem(
        label = "Account",
        icon = Icons.Filled.Home,
        node = TopBarNode(PagerNode.context, "Settings / Account", Icons.Filled.Home) {},
        selected = false
    ),
    NavigationNodeItem(
        label = "Profile",
        icon = Icons.Filled.Edit,
        node = TopBarNode(PagerNode.context, "Settings / Profile", Icons.Filled.Edit) {},
        selected = false
    ),
    NavigationNodeItem(
        label = "About Us",
        icon = Icons.Filled.Email,
        node = TopBarNode(PagerNode.context, "Settings / About Us", Icons.Filled.Email) {},
        selected = false
    )
)

val navbarNavItems = mutableListOf(
    NavigationNodeItem(
        label = "Home",
        icon = Icons.Filled.Home,
        node = TopBarNode(NavBarNode.context, "Home", Icons.Filled.Home) {},
        selected = false
    ),
    NavigationNodeItem(
        label = "Orders",
        icon = Icons.Filled.Edit,
        node = TopBarNode(NavBarNode.context, "Orders", Icons.Filled.Edit) {},
        selected = false
    ),
    NavigationNodeItem(
        label = "Settings",
        icon = Icons.Filled.Email,
        node = PagerNode.also { it.setNavItems(pagerNavItems, 0) },
        selected = false
    )
)

return NavBarNode.also { it.setNavItems(navbarNavItems, 0) }
```

<H3>Contribute</H3>

This project has no license or owner, you can copy and paste it, use it or reuse it, modify, alter
or do whatever you want with it. Any feedback or contribution would be greatly appreciated. Thanks!!

## How to run
*Prerequisites*: to run on iOS and Android, you should have "Kotlin Multiplatform Mobile" plugin
installed either in Android Studio or in AppCode with [installed CocoaPods](https://kotlinlang.org/docs/native-cocoapods.html).

### Run on Android via Gradle
- connect device or emulator
- `./gradlew androidApp:installDebug`
- open app

Choose a run configuration for an appropriate target in IDE and run it.

![run-configurations.png](run-configurations.png)

To run on iOS device, please correct `iosApp/Configuration/TeamId.xcconfig` with your Apple Team ID.
Alternatively, you may setup signing within XCode opening `iosApp/FallingBalls.xcworkspace` and then
using "Signing & Capabilities" tab of `FallingBalls` target. See also how to prepare XCode section below.

Then choose **iosApp** configuration in IDE and run it
(may also be referred as `FallingBalls` in the Run Configurations or `iosApp_` for Android studio).

### Run on desktop via Gradle
`./gradlew desktopApp:run`

### Run JS in browser with WebAssembly Skia via Gradle

`./gradlew jsApp:jsBrowserDevelopmentRun`

### Prepare Xcode to launch on iOS device

1) **Add your Apple ID.**  
   Xcode -> Preferences... -> Accounts  
   ![apple-id.png](apple-id.png)

2) **Create certificates with XCode in temporary project.**  
   You can create a certificate in Xcode. Create a new iOS app in Xcode.  
   File -> New -> Project  
   Choose iOS, App:  
   ![ios-app.png](ios-app.png)

3) **In next dialog set any product name and choose your teamID.**

4) **Run project on iOS Device.**  
   Xcode prompts you to install the certificate. Require password from login.keychain

5) **Done**
   After a successful launch on the device - you can delete this temporary project.  
   Certificates will remain in login.keychain

### Run native on MacOS
Choose **shared[macosX64]** or **shared[macosArm64]** configuration in IDE and run it.