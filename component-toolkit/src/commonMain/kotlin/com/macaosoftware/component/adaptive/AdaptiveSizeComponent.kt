package com.macaosoftware.component.adaptive

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.unit.Constraints
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.ComponentLifecycleState
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.core.deeplink.DeepLinkResult
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.core.transferFrom
import com.macaosoftware.component.util.EmptyNavigationComponentView

/**
 * This node is basically a proxy, it transfer request and events to its active child node
 * */
class AdaptiveSizeComponent : Component() {

    private val initialEmptyNavComponent: NavigationComponent = AdaptiveSizeStubNavComponent()
    private var CompactNavComponent: NavigationComponent = AdaptiveSizeStubNavComponent()
    private var MediumNavComponent: NavigationComponent = AdaptiveSizeStubNavComponent()
    private var ExpandedNavComponent: NavigationComponent = AdaptiveSizeStubNavComponent()
    private var currentNavComponent = mutableStateOf(initialEmptyNavComponent)

    var navItems: MutableList<NavItem> = currentNavComponent.value.navItems
    var selectedIndex: Int = currentNavComponent.value.selectedIndex
    var childComponents: MutableList<Component> = mutableListOf()

    private var currentWindowSizeInfo: WindowSizeInfo? = null

    fun setNavItems(navItems: MutableList<NavItem>, selectedIndex: Int) {
        this.navItems = navItems
        this.selectedIndex = selectedIndex
        currentNavComponent.value.setNavItems(navItems, selectedIndex)
    }

    fun setCompactContainer(navComponent: NavigationComponent) {
        CompactNavComponent = navComponent
        attachChildComponent(navComponent)
    }

    fun setMediumContainer(navComponent: NavigationComponent) {
        MediumNavComponent = navComponent
        attachChildComponent(navComponent)
    }

    fun setExpandedContainer(navComponent: NavigationComponent) {
        ExpandedNavComponent = navComponent
        attachChildComponent(navComponent)
    }

    private fun attachChildComponent(navComponent: NavigationComponent) {
        navComponent.getComponent().setParent(this@AdaptiveSizeComponent)
        childComponents.add(navComponent.getComponent())
    }

    override fun onStart() {
        println("${instanceId()}::onStart()")
        currentNavComponent.value.getComponent().dispatchStart()
    }

    override fun onStop() {
        println("${instanceId()}::onStop()")
        currentNavComponent.value.getComponent().dispatchStop()
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        val nextComponent = currentNavComponent.value.getComponent()
        nextComponent.uriFragment = nextUriFragment
        return nextComponent
    }

    override fun onDeepLinkNavigateTo(
        matchingComponent: Component
    ): DeepLinkResult {
        println("${instanceId()}.onDeepLinkMatch() matchingNode = ${matchingComponent.instanceId()}")
        return DeepLinkResult.Success(matchingComponent)
    }

    fun getComponent(): Component {
        return currentNavComponent.value.getComponent()
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("${instanceId()}.Composing() lifecycleState = $lifecycleState")
        val componentLifecycleState by lifecycleStateFlow.collectAsState(ComponentLifecycleState.Created)
        when (componentLifecycleState) {
            ComponentLifecycleState.Created,
            ComponentLifecycleState.Destroyed -> {
            }

            ComponentLifecycleState.Started -> {
                /*
                // Uncomment this code to see the performance difference. You will notice a small
                // glitch when switching from Panel to BottomNavigation for instance
                BoxWithConstraints(Modifier.fillMaxSize()) {
                    val boxConstraints = this
                    val windowSizeInfo = WindowSizeInfo.fromWidthDp(boxConstraints.maxWidth)

                    val currentNavComponentCopy = currentNavComponent.value
                    if (currentNavComponentCopy == initialEmptyNavComponent) {
                        setAndStartNavComponent(windowSizeInfo)
                    } else if (currentNavComponentCopy != windowSizeInfo) {
                        transferNavComponent(windowSizeInfo)
                    }

                    StartedContent(modifier, currentNavComponentCopy)
                }
                */

                // Bellow is the most efficient adaptive selector I have found
                AdaptiveChildComponentSelector(modifier.fillMaxSize())
            }

            ComponentLifecycleState.Stopped -> {
            }
        }
    }

    @Composable
    private fun AdaptiveChildComponentSelector(modifier: Modifier = Modifier) {
        val adaptiveSelectorScope = remember(key1 = this@AdaptiveSizeComponent) {
            AdaptiveSelectorScopeImpl()
        }
        Layout(
            // Since we invoke it here it will have Size.Zero
            // on Composition then will have size value below
            content = { adaptiveSelectorScope.AdaptiveView() },
            modifier = modifier
        ) { measurables: List<Measurable>, constraints: Constraints ->
            adaptiveSelectorScope.maxWidthDp.value = constraints.maxWidth.toDp()
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }
            layout(constraints.maxWidth, constraints.maxHeight) {
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = 0)
                }
            }
        }
    }

    @Composable
    private fun AdaptiveSelectorScope.AdaptiveView() {
        val windowSizeInfo = windowSizeInfoState.value
        println("AdaptiveSizeComponent::AdaptiveView.Composing windowSizeInfo = $windowSizeInfo")
        if (currentNavComponent.value == initialEmptyNavComponent) {
            setAndStartNavComponent(windowSizeInfo)
        } else if (currentWindowSizeInfo != windowSizeInfo) {
            currentWindowSizeInfo = windowSizeInfo
            transferNavComponent(windowSizeInfo)
        }
        StartedContent(Modifier, currentNavComponent.value)
    }

    /**
     * The Content when this component is in Started State.
     * */
    @Composable
    private fun StartedContent(modifier: Modifier, currentNavComponent: NavigationComponent?) {
        val currentComponent = currentNavComponent?.getComponent()
        if (currentComponent != null) {
            currentComponent.Content(modifier)
        } else {
            EmptyNavigationComponentView(this@AdaptiveSizeComponent)
        }
    }

    private fun setAndStartNavComponent(windowSizeInfo: WindowSizeInfo) {
        val navComponent = when (windowSizeInfo) {
            WindowSizeInfo.Compact -> CompactNavComponent
            WindowSizeInfo.Medium -> MediumNavComponent
            WindowSizeInfo.Expanded -> ExpandedNavComponent
        }
        navComponent.setNavItems(navItems, selectedIndex)
        navComponent.getComponent().dispatchStart()
        currentNavComponent.value = navComponent
    }

    private fun transferNavComponent(
        windowSizeInfo: WindowSizeInfo
    ) {
        println("AdaptiveSizeComponent::transferNavComponent")
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
        donorNavComponent: NavigationComponent,
        adoptingNavComponent: NavigationComponent
    ): NavigationComponent {
        if (adoptingNavComponent == donorNavComponent) {
            return adoptingNavComponent
        }
        return adoptingNavComponent.apply { transferFrom(donorNavComponent) }
    }

}
