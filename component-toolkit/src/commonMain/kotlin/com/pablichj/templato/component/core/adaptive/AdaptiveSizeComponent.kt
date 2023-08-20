package com.pablichj.templato.component.core.adaptive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentLifecycleState
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.transferFrom
import com.pablichj.templato.component.core.util.EmptyNavigationComponentView

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

    override fun onDeepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        println("${instanceId()}.onDeepLinkMatch() matchingNode = ${matchingComponent.instanceId()}")
        return DeepLinkResult.Success
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
                BoxWithConstraints(Modifier.fillMaxSize()) {
                    val boxConstraints = this
                    val currentNavComponentCopy = currentNavComponent.value
                    val windowSizeInfo = WindowSizeInfo.fromWidthDp(boxConstraints.maxWidth)

                    if (currentNavComponentCopy == initialEmptyNavComponent) {
                        setAndStartNavComponent(windowSizeInfo)
                    } else if (currentNavComponentCopy != windowSizeInfo) {
                        transferNavComponent(windowSizeInfo)
                    }

                    StartedContent(modifier, currentNavComponentCopy)
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
