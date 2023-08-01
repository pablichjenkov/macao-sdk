package com.pablichj.templato.component.core.adaptive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentLifecycleState
import com.pablichj.templato.component.core.EmptyStackMessage
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.router.DeepLinkResult
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.stack.BackStack
import com.pablichj.templato.component.core.transferFrom

/**
 * This node is basically a proxy, it transfer request and events to its active child node
 * */
class AdaptiveSizeComponent : Component(), NavigationComponent {
    private val initialEmptyNavComponent: NavigationComponent = AdaptiveSizeStubNavComponent()
    private var CompactNavComponent: NavigationComponent = AdaptiveSizeStubNavComponent()
    private var MediumNavComponent: NavigationComponent = AdaptiveSizeStubNavComponent()
    private var ExpandedNavComponent: NavigationComponent = AdaptiveSizeStubNavComponent()
    private var currentNavComponent = mutableStateOf(initialEmptyNavComponent)

    override val backStack: BackStack<Component> = currentNavComponent.value.backStack
    override var navItems: MutableList<NavItem> = currentNavComponent.value.navItems
    override var childComponents: MutableList<Component> = mutableListOf()
    override var selectedIndex: Int = currentNavComponent.value.selectedIndex
    // Do not use activeComponent, is always null, use currentNavComponent instead
    override var activeComponent: MutableState<Component?> = mutableStateOf(null)

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

    override var uriFragment: String? = "_navigator_adaptive"

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        val nextComponent = currentNavComponent.value.getComponent()
        nextComponent.uriFragment = nextUriFragment
        return nextComponent
    }

    override fun onDeepLinkNavigation(matchingComponent: Component): DeepLinkResult {
        println("${instanceId()}.onDeepLinkMatch() matchingNode = ${matchingComponent.instanceId()}")
        return DeepLinkResult.Success
    }

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

    @Composable
    override fun Content(modifier: Modifier) {
        println("${instanceId()}.Composing() lifecycleState = $lifecycleState")
        val density = LocalDensity.current
        val componentLifecycleState by lifecycleStateFlow.collectAsState(ComponentLifecycleState.Created)
        when (componentLifecycleState) {
            ComponentLifecycleState.Created,
            ComponentLifecycleState.Destroyed -> {
            }
            ComponentLifecycleState.Started -> {
                val windowSizeInfo = remember(key1 = this) { mutableStateOf<WindowSizeInfo?>(null) }
                val windowSizeInfoDiff = derivedStateOf { windowSizeInfo.value }
                Box(Modifier.fillMaxSize().onSizeChanged { size ->
                    val widthDp = with(density) { size.width.toDp() }
                    println("${instanceId()}::Box.onSizeChanged Width of Text in Pixels: ${size.width}")
                    println("${instanceId()}::Box.onSizeChanged Width of Text in DP: $widthDp")
                    windowSizeInfo.value = WindowSizeInfo.fromWidthDp(widthDp)
                }) {
                    val windowSizeInfoCopy = windowSizeInfoDiff.value
                    println("${instanceId()}.Composing.Started() windowSizeInfo = $windowSizeInfoCopy")
                    val currentNavComponentCopy = currentNavComponent.value
                    if (windowSizeInfoCopy != null) {
                        if (currentNavComponentCopy == initialEmptyNavComponent) {
                            setAndStartNavComponent(windowSizeInfoCopy)
                        } else {
                            transferNavComponent(windowSizeInfoCopy)
                        }
                        StartedContent(modifier, currentNavComponentCopy)
                    }
                }
            }
            ComponentLifecycleState.Stopped -> {
            }
        }
    }

    @Composable
    private fun StartedContent(modifier: Modifier, currentNavComponent: NavigationComponent?) {
        val currentComponent = currentNavComponent?.getComponent()

        if (currentComponent != null) {
            currentComponent.Content(modifier)
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = "${instanceId()} $EmptyStackMessage",
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
        navComponent.getComponent().dispatchStart()
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
        donorNavComponent: NavigationComponent,
        adoptingNavComponent: NavigationComponent
    ): NavigationComponent {
        if (adoptingNavComponent == donorNavComponent) {
            return adoptingNavComponent
        }
        return adoptingNavComponent.apply { transferFrom(donorNavComponent) }
    }

}