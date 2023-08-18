## Component Toolkit
The component-toolkit is basically another state management library. At its core, a Component is nothing else than a State Holder helper class. In this case, the components or state holders will form a tree structure. Components can interact with each other directly or indirectly. In a direct manner, a parent component will create or hoist children components so it knows the children types and functionality.
In another way, a Component can navigate to another Component in the tree that is not necessarely a children or parent. Using Component deep linking, any Component in the tree can talk to another Component, send requests and receive results back.

#### Guides
1. Simple Component
2. Navigation Component
3. Components Deep Linking
4. Request/Result between Components

#### Show me some code

```kotlin
// An example of how to make a component tree. In this case a DrawerComponent that will have a 
// BottomBarComponent as one of its children.
object ComponentTreeBuilder {

    fun build(): DrawerComponent {

        val drawerComponent = DrawerComponent()

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Home", Icons.Filled.Home) {},
                selected = false
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = buildNavBarComponent(),
                selected = false
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = TopBarComponent("Settings", Icons.Filled.Email) {},
                selected = false
            )
        )

        return drawerComponent.also { it.setItems(drawerNavItems, 0) }
    }

    private fun buildNavBarComponent(): NavBarComponent {

        val navBarComponent = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Active",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Orders/Active", Icons.Filled.Home) {},
                selected = false
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Settings,
                component = TopBarComponent("Orders/Past", Icons.Filled.Settings) {},
                selected = false
            ),
            NavItem(
                label = "New Order",
                icon = Icons.Filled.Add,
                component = TopBarComponent("Orders/New Order", Icons.Filled.Add) {},
                selected = false
            )
        )

        return navBarComponent.also { it.setItems(navbarNavItems, 0) }
    }

}

// Once you have a tree, then just call Content(Modifier) on the root component. The compose 
// machinery will traverse the tree painting on the screen each active component.Content(Modifier).

fun main() = application {

    val drawerComponent: DrawerComponent = remember(key1 = this) {
        DrawerTreeBuilder.build()
    }

   DesktopComponentRender(
      rootComponent = drawerComponent,
      onBackPress = { exitProcess(0) }
   )
   
}
```

Above code will produce the desktop application shown in the video below. The **DesktopAppComponent** is
a demo component that consists of, an **AdaptiveSizeComponent** as a parent of 3 **NavigationComponents**(Drawer,
BottomBar, Panel) that share children of type **StackComponents** which contain single page Components(
Page1, Page2, Page3). When the window size changes, the **DesktopAppComponent** sets the corresponding **
NavigatorComponent** as active, and the children Components are tranfered from one navigator parent to the new
active navigator parent. In the video there is also a demonstration of how deep links work. Deep
links are represented as a path in the state tree, each subpath represents a Component. When a deep link
path is traversed, each Component represented in a subpath is activated, all the way upto the last Component.
The Component path can be mapped to a web url.

https://user-images.githubusercontent.com/5303301/209282619-06748ddc-3fb1-4a74-8849-0c2215bbafc4.mp4

Lets see how the Android code will look like for the same NavigatonDrawer type of Application.

```kotlin
class DrawerActivity : ComponentActivity() {

    private val stateTreeHolder by viewModels<DrawerStateTreeHolder>()
    private lateinit var stateTree: Component

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // It creates a state tree where the root Component is a NavigationDrawer
        stateTree = stateTreeHolder.getOrCreate().apply {
            context.rootComponentBackPressedDelegate = ForwardBackPressCallback { finish() }
        }

        setContent {
            MaterialTheme {
                CompositionLocalProvider(
                    LocalBackPressedDispatcher provides AndroidBackPressDispatcher(this@DrawerActivity),
                ) {
                    stateTree.Content(Modifier)
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

<H4>Navigation Components Nesting</H4>

In the case of large screens you can have multiple Navigation components in a split screen or you could
nest them within another Navigation component. In the next video, one of the screens is splitted in two
sections each with a navivation drawer on its own. Observe than switching pages in the outer
NavigationDrawer doesn't affect the state of those inner NavigationDrawers. This is possible because
each parent component contains its own navigation state and is not affected by being switched/swapped by
a sibling component.

https://user-images.githubusercontent.com/5303301/208034085-7a8cf47c-3339-45b0-b411-b94be1f5f974.mov

or in the device, although is not to practical in this case.

https://user-images.githubusercontent.com/5303301/205610976-ce7e3006-bbdf-4f42-941c-de784714b6cd.mp4

<H4>Adaptable UI</H4>

A NavigatorComponent can be replaced with another NavigatorComponent at any point in time while the App is
running. All you have to do is transfer children components from one NavigatorComponent to the other and
attach the NavigatorComponent to the parent Component. Automatically the tree will refresh its content. See
AdaptableWindowComponent for an example.

https://user-images.githubusercontent.com/5303301/206708221-a8d13577-f38d-4b07-bcbf-f66cbca26d46.mov

The corresponding scenario in a phone

https://user-images.githubusercontent.com/5303301/206601512-84ff3d70-28e8-4cb3-bbf6-67f134f6fe08.mov

Above videos show how the App switches between a **DrawerComponent** and a **PanelComponent**, but it could be
any Component that implements NavigatorComponent interface. Eg: NavBarComponent, PagerComponent, YourCustomNavigatorComponent
etc

<H3>Library Concepts</H3>

The pattern consists of designing your App navigation as if it was a Tree data structure. Users can
visit the different components of the tree at any desired time. When the user modifies a component and visit a
different component, the component state remains as the user left it. The next time the user comes back to
that component, it will have the same state when it was last visited.

<H4>Component</H4>

A **Component** is the class used to build the State Tree.

Each ***Component*** is in charge of hadling **back press** events as well as Activity Lifecycle events
like start and stop. Also each ***Component*** is responsible for propagating Start/Stop events to its
children components. In the case of a back pressed events, the top most component will have the opportunity to
first process the event. In case no processing is needed, it has the responsability to pass the
event up to its parent component. Then the parent do the same until the event reaches the root component.

An example of a ***Component*** implementation that handles its children as a back stack is the ***
BackStackComponent***. It will have only one Active child at any time and this child will ocuppy the
entire Viewport assigned to the ***StackComponent***. The pattern allows to build any type of nested
navigation within the tree. It is a matter of just placing a ***Component***
implementation in the tree and handle the children components whatever the way you want.

The ***Component*** abstract class produces a Composable content representing its State every time
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

<H3>Build the Tree</H3>
Creating a tree of components is simple. Start by the root component and append child components to it. Some components
may have already a predifined child types that it knows how to handle. Bellow is an example of how
to build a bottom bar navigator component. Check the other examples.

```kotlin
        val NavBarComponent = NavBarComponent(rootParentComponentContext)

val PagerComponent = PagerComponent(NavBarComponent.context)

val pagerNavItems = mutableListOf(
    NavigationComponentItem(
        label = "Account",
        icon = Icons.Filled.Home,
        component = TopBarComponent(PagerComponent.context, "Settings / Account", Icons.Filled.Home) {},
        selected = false
    ),
    NavigationComponentItem(
        label = "Profile",
        icon = Icons.Filled.Edit,
        component = TopBarComponent(PagerComponent.context, "Settings / Profile", Icons.Filled.Edit) {},
        selected = false
    ),
    NavigationComponentItem(
        label = "About Us",
        icon = Icons.Filled.Email,
        component = TopBarComponent(PagerComponent.context, "Settings / About Us", Icons.Filled.Email) {},
        selected = false
    )
)

val navbarNavItems = mutableListOf(
    NavigationComponentItem(
        label = "Home",
        icon = Icons.Filled.Home,
        component = TopBarComponent(NavBarComponent.context, "Home", Icons.Filled.Home) {},
        selected = false
    ),
    NavigationComponentItem(
        label = "Orders",
        icon = Icons.Filled.Edit,
        component = TopBarComponent(NavBarComponent.context, "Orders", Icons.Filled.Edit) {},
        selected = false
    ),
    NavigationComponentItem(
        label = "Settings",
        icon = Icons.Filled.Email,
        component = PagerComponent.also { it.setNavItems(pagerNavItems, 0) },
        selected = false
    )
)

return NavBarComponent.also { it.setNavItems(navbarNavItems, 0) }
```

<H3>Contribute</H3>

Contributions of any type are welcome!
