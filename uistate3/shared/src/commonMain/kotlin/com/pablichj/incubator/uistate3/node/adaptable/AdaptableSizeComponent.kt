package com.pablichj.incubator.uistate3.node.adaptable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult

/**
 * This node is basically a proxy, it transfer request and events to its active child node
 * */
class AdaptableSizeComponent(
    var windowSizeInfoProvider: IWindowSizeInfoProvider
) : Component() {

    private var navItems: MutableList<NavItem> = mutableListOf()
    private var startingPosition: Int = 0
    private var CompactNavComponent: INavComponent? = null
    private var MediumNavComponent: INavComponent? = null
    private var ExpandedNavComponent: INavComponent? = null
    private var currentNavComponent =
        mutableStateOf<INavComponent?>(null) // todo: This should be a reactive state

    fun setNavItems(navItems: MutableList<NavItem>, startingPosition: Int) {
        this.navItems = navItems
        this.startingPosition = startingPosition
        currentNavComponent.value?.setNavItems(navItems, startingPosition)
    }

    fun setCompactContainer(INavComponent: INavComponent) {
        CompactNavComponent = INavComponent
        INavComponent.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    fun setMediumContainer(INavComponent: INavComponent) {
        MediumNavComponent = INavComponent
        INavComponent.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    fun setExpandedContainer(INavComponent: INavComponent) {
        ExpandedNavComponent = INavComponent
        INavComponent.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    override fun start() {
        super.start()
        println("$clazz::start()")
        currentNavComponent.value?.getComponent()?.start()
    }

    override fun stop() {
        super.stop()
        println("$clazz::stop()")
        currentNavComponent.value?.getComponent()?.stop()
    }

    // region: DeepLink

    override fun getDeepLinkSubscribedList(): List<Component> {
        return listOfNotNull(
            CompactNavComponent?.getComponent(),
            MediumNavComponent?.getComponent(),
            ExpandedNavComponent?.getComponent()
        )
    }

    override fun onDeepLinkMatchingNode(matchingComponent: Component): DeepLinkResult {
        println("$clazz.onDeepLinkMatchingNode() matchingNode = ${matchingComponent.clazz}")
        return DeepLinkResult.Success
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

                if (currentNavComponentCopy == null) {
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
        navComponent?.setNavItems(navItems, startingPosition)
        navComponent?.getComponent()?.start()
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
        donorNavComponent: INavComponent?,
        adoptingNavComponent: INavComponent?
    ): INavComponent? {

        if (adoptingNavComponent == donorNavComponent) {
            return adoptingNavComponent
        }

        val adoptingNavigatorCopy = adoptingNavComponent ?: return donorNavComponent

        return if (donorNavComponent == null) { // The first time when no node has been setup yet
            adoptingNavComponent.setNavItems(navItems, startingPosition)
            adoptingNavComponent
        } else { // do the real transfer here
            adoptingNavigatorCopy.transferFrom(donorNavComponent)
            adoptingNavigatorCopy
        }
    }

}