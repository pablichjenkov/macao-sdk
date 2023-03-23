package com.pablichj.incubator.uistate3.node.adaptable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.backpress.BackStack
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult

/**
 * This node is basically a proxy, it transfer request and events to its active child node
 * */
open class AdaptableSizeComponent() : Component(), NavComponent {
    private val initialEmptyNavComponent: NavComponent = AdaptableSizeStubNavComponent()
    private var CompactNavComponent: NavComponent = AdaptableSizeStubNavComponent()
    private var MediumNavComponent: NavComponent = AdaptableSizeStubNavComponent()
    private var ExpandedNavComponent: NavComponent = AdaptableSizeStubNavComponent()
    private var currentNavComponent = mutableStateOf(initialEmptyNavComponent)

    override val backStack: BackStack<Component> = currentNavComponent.value.backStack
    override var navItems: MutableList<NavItem> = currentNavComponent.value.navItems
    override var childComponents: MutableList<Component> = mutableListOf()
    override var selectedIndex: Int = currentNavComponent.value.selectedIndex
    override var activeComponent: MutableState<Component?> =
        currentNavComponent.value.activeComponent

    fun setNavItems(navItems: MutableList<NavItem>, selectedIndex: Int) {
        this.navItems = navItems
        this.selectedIndex = selectedIndex
        currentNavComponent.value.setNavItems(navItems, selectedIndex)
    }

    fun setCompactContainer(navComponent: NavComponent) {
        CompactNavComponent = navComponent
        attachChildComponent(navComponent)
    }

    fun setMediumContainer(navComponent: NavComponent) {
        MediumNavComponent = navComponent
        attachChildComponent(navComponent)
    }

    fun setExpandedContainer(navComponent: NavComponent) {
        ExpandedNavComponent = navComponent
        attachChildComponent(navComponent)
    }

    private fun attachChildComponent(navComponent: NavComponent) {
        navComponent.getComponent().setParent(this@AdaptableSizeComponent)
        childComponents.add(navComponent.getComponent())
    }

    override fun start() {
        super.start()
        println("$clazz::start()")
        currentNavComponent.value.getComponent().start()
    }

    override fun stop() {
        super.stop()
        println("$clazz::stop()")
        currentNavComponent.value.getComponent().stop()
    }

    // region: DeepLink

    override fun getDeepLinkSubscribedList(): List<Component> {
        return listOfNotNull(
            CompactNavComponent.getComponent(),
            MediumNavComponent.getComponent(),
            ExpandedNavComponent.getComponent()
        )
    }

    override fun onDeepLinkNavigation(matchingComponent: Component): DeepLinkResult {
        println("$clazz.onDeepLinkMatch() matchingNode = ${matchingComponent.clazz}")
        return DeepLinkResult.Success
    }

    // endregion

    // region: INavComponent

    override fun getComponent(): Component {
        return currentNavComponent.value.getComponent()
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        currentNavComponent.value.onSelectNavItem(selectedIndex, navItems)
    }

    override fun updateSelectedNavItem(newTop: Component) {
        currentNavComponent.value.updateSelectedNavItem(newTop)
    }

    override fun onDestroyChildComponent(component: Component) {
        currentNavComponent.value.onDestroyChildComponent(component)
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        println("$clazz.Composing() lifecycleState = $lifecycleState")
        val density = LocalDensity.current
        val componentLifecycleState by componentLifecycleFlow.collectAsState(ComponentLifecycleState.Created)
        when (componentLifecycleState) {
            ComponentLifecycleState.Created,
            ComponentLifecycleState.Destroyed -> {
            }
            ComponentLifecycleState.Started -> {
                val windowSizeInfo = remember {
                    mutableStateOf<WindowSizeInfo>(WindowSizeInfo.Compact)
                }
                Box(Modifier.fillMaxSize().onSizeChanged { size ->
                    val widthDp = with(density) { size.width.toDp() }
                    println("$clazz::Box.onSizeChanged Width of Text in Pixels: ${size.width}")
                    println("$clazz::Box.onSizeChanged Width of Text in DP: $widthDp")
                    windowSizeInfo.value = WindowSizeInfo.fromWidthDp(widthDp)
                }) {
                    println("$clazz.Composing.Started() windowSizeInfo = $windowSizeInfo")
                    val currentNavComponentCopy = currentNavComponent.value

                    if (currentNavComponentCopy == initialEmptyNavComponent) {
                        setAndStartNavComponent(windowSizeInfo.value)
                    } else {
                        transferNavComponent(windowSizeInfo.value)
                    }

                    StartedContent(modifier, currentNavComponentCopy)
                }
            }
            ComponentLifecycleState.Stopped -> {
            }
        }
    }

    @Composable
    private fun StartedContent(modifier: Modifier, currentNavComponent: NavComponent?) {
        val currentComponent = currentNavComponent?.getComponent()

        if (currentComponent != null) {
            currentComponent.Content(modifier)
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = "AdaptableSizeComponent currentNavComponent is empty. " +
                            "Please add some children NavComponent",
                    textAlign = TextAlign.Center
                )
            }
        }

    }

    private fun setAndStartNavComponent(windowSizeInfo: WindowSizeInfo) {
        val navComponent = when (windowSizeInfo) {
            WindowSizeInfo.Compact -> CompactNavComponent
            WindowSizeInfo.Medium -> MediumNavComponent
            WindowSizeInfo.Expanded -> ExpandedNavComponent
        }
        navComponent.setNavItems(navItems, selectedIndex)
        navComponent.getComponent().start()
        currentNavComponent.value = navComponent
    }

    private fun transferNavComponent(
        windowSizeInfo: WindowSizeInfo
    ) {
        currentNavComponent.value = when (windowSizeInfo) {
            WindowSizeInfo.Compact -> {
                transfer(currentNavComponent.value, CompactNavComponent)
            }
            WindowSizeInfo.Medium -> {
                transfer(currentNavComponent.value, MediumNavComponent)
            }
            WindowSizeInfo.Expanded -> {
                transfer(currentNavComponent.value, ExpandedNavComponent)
            }
        }
    }

    private fun transfer(
        donorNavComponent: NavComponent,
        adoptingNavComponent: NavComponent
    ): NavComponent {
        if (adoptingNavComponent == donorNavComponent) {
            return adoptingNavComponent
        }
        return adoptingNavComponent.apply { transferFrom(donorNavComponent) }
    }

}