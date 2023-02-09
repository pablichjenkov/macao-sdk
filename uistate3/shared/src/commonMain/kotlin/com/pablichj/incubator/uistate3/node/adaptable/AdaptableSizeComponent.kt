package com.pablichj.incubator.uistate3.node.adaptable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.backstack.BackStack
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult

/**
 * This node is basically a proxy, it transfer request and events to its active child node
 * */
class AdaptableSizeComponent(
    var windowSizeInfoProvider: IWindowSizeInfoProvider
) : Component(), INavComponent {


    private var CompactNavComponent: INavComponent = AdaptableSizeStubComponent()
    private var MediumNavComponent: INavComponent = AdaptableSizeStubComponent()
    private var ExpandedNavComponent: INavComponent = AdaptableSizeStubComponent()
    private var currentNavComponent = mutableStateOf(CompactNavComponent)

    override val backStack: BackStack<Component> = currentNavComponent.value.backStack
    override var navItems: MutableList<NavItem> = currentNavComponent.value.navItems
    override var childComponents = currentNavComponent.value.childComponents
    override var selectedIndex: Int = currentNavComponent.value.selectedIndex
    override var activeComponent: MutableState<Component?> =
        currentNavComponent.value.activeComponent

    fun setNavItems(navItems: MutableList<NavItem>, selectedIndex: Int) {
        this.navItems = navItems
        this.selectedIndex = selectedIndex
        currentNavComponent.value.setNavItems(navItems, selectedIndex)
    }

    fun setCompactContainer(navComponent: INavComponent) {
        CompactNavComponent = navComponent
        navComponent.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    fun setMediumContainer(navComponent: INavComponent) {
        MediumNavComponent = navComponent
        navComponent.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    fun setExpandedContainer(navComponent: INavComponent) {
        ExpandedNavComponent = navComponent
        navComponent.getComponent().attachToParent(this@AdaptableSizeComponent)
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

    override fun onDeepLinkMatch(matchingComponent: Component): DeepLinkResult {
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
        val componentLifecycleState by componentLifecycleFlow.collectAsState(LifecycleState.Created)
        when (componentLifecycleState) {
            LifecycleState.Created,
            LifecycleState.Destroyed -> {
            }
            LifecycleState.Started -> {
                val windowSizeInfo by windowSizeInfoProvider.windowSizeInfo()
                println("Pablo $clazz.Composing.Started() windowSizeInfo = $windowSizeInfo")

                val currentNavComponentCopy = currentNavComponent.value

                if (currentNavComponentCopy is AdaptableSizeStubComponent) {
                    SetNavComponentContent(windowSizeInfo)
                } else {
                    TransferNavComponentContent(windowSizeInfo)
                }

                StartedContent(modifier, currentNavComponentCopy)
            }
            LifecycleState.Stopped -> {
            }
        }
    }

    @Composable
    private fun StartedContent(modifier: Modifier, currentNavComponent: INavComponent?) {
        val currentComponent = currentNavComponent?.getComponent()

        if (currentComponent != null) {
            currentComponent.Content(modifier)
        } else {
            Box {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = "Empty Stack, Please add some children",
                    textAlign = TextAlign.Center
                )
            }
        }

    }

    @Composable
    private fun SetNavComponentContent(
        windowSizeInfo: WindowSizeInfo
    ) {
        val navComponent = when (windowSizeInfo) {
            WindowSizeInfo.Compact -> CompactNavComponent
            WindowSizeInfo.Medium -> MediumNavComponent
            WindowSizeInfo.Expanded -> ExpandedNavComponent
        }
        navComponent.setNavItems(navItems, selectedIndex)
        navComponent.getComponent().start()
        currentNavComponent.value = navComponent
    }

    @Composable
    private fun TransferNavComponentContent(
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
        donorNavComponent: INavComponent,
        adoptingNavComponent: INavComponent
    ): INavComponent {

        if (adoptingNavComponent == donorNavComponent) {
            return adoptingNavComponent
        }

        val adoptingNavigatorCopy = adoptingNavComponent ?: return donorNavComponent

        return if (donorNavComponent is AdaptableSizeStubComponent) { // The first time when no node has been setup yet
            adoptingNavComponent.setNavItems(navItems, selectedIndex)
            adoptingNavComponent
        } else { // do the real transfer here
            adoptingNavigatorCopy.transferFrom(donorNavComponent)
            adoptingNavigatorCopy
        }
    }

}