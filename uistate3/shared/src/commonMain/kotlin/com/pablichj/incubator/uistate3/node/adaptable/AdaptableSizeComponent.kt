package com.pablichj.incubator.uistate3.node.adaptable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.node.INavComponent
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkResult
import com.pablichj.incubator.uistate3.node.navigation.Path
import com.pablichj.incubator.uistate3.node.setItems

/**
 * This node is basically a proxy, it transfer request and events to its active child node
 * */
class AdaptableSizeComponent(
    var windowSizeInfoProvider: IWindowSizeInfoProvider
) : Component() {

    private var navItems: MutableList<NodeItem> = mutableListOf()
    private var startingPosition: Int = 0
    private var CompactNavigator: INavComponent? = null
    private var MediumNavigator: INavComponent? = null
    private var ExpandedNavigator: INavComponent? = null
    private var currentNavComponent =
        mutableStateOf<INavComponent?>(null) // todo: This should be a reactive state

    fun setNavItems(navItems: MutableList<NodeItem>, startingPosition: Int) {
        this.navItems = navItems
        this.startingPosition = startingPosition
        currentNavComponent.value?.setItems(navItems, startingPosition)
    }

    fun setCompactContainer(INavComponent: INavComponent) {
        CompactNavigator = INavComponent
        INavComponent.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    fun setMediumContainer(INavComponent: INavComponent) {
        MediumNavigator = INavComponent
        INavComponent.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    fun setExpandedContainer(INavComponent: INavComponent) {
        ExpandedNavigator = INavComponent
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

    override fun getDeepLinkNodes(): List<Component> {
        return listOfNotNull(
            CompactNavigator?.getComponent(),
            MediumNavigator?.getComponent(),
            ExpandedNavigator?.getComponent()
        )
    }

    override fun onCheckChildMatchHandler(advancedPath: Path, matchingComponent: Component): DeepLinkResult {
        val interceptingNode = currentNavComponent.value?.getComponent() ?: matchingComponent
        interceptingNode.subPath = matchingComponent.subPath.copy()
        return interceptingNode.checkDeepLinkMatch(advancedPath)
    }

    override fun onNavigateChildMatchHandler(
        advancedPath: Path,
        matchingComponent: Component
    ): DeepLinkResult {
        val interceptingNode = currentNavComponent.value?.getComponent() ?: matchingComponent
        interceptingNode.subPath = matchingComponent.subPath.copy()
        onDeepLinkMatchingNode(interceptingNode)
        return interceptingNode.navigateUpToDeepLink(advancedPath)
    }

    override fun onDeepLinkMatchingNode(matchingComponent: Component) {
        println("AdaptableWindowNode.onDeepLinkMatchingNode() matchingNode = ${matchingComponent.subPath}")
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        println("AdaptableWindowNode.Composing() lifecycleState = ${lifecycleState}")

        val windowSizeInfo by windowSizeInfoProvider.windowSizeInfo()

        //val nodeLifecycleState by nodeLifecycleFlow.collectAsState(LifecycleState.Created)

        currentNavComponent.value = when (windowSizeInfo) {
            WindowSizeInfo.Compact -> {
                tryTransfer(currentNavComponent.value, CompactNavigator)
            }
            WindowSizeInfo.Medium -> {
                tryTransfer(currentNavComponent.value, MediumNavigator)
            }
            WindowSizeInfo.Expanded -> {
                tryTransfer(currentNavComponent.value, ExpandedNavigator)
            }
        }

        val CurrentNode = currentNavComponent.value?.getComponent()

        if (CurrentNode != null) {
            CurrentNode.Content(modifier)
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

    private fun tryTransfer(
        donorNavComponent: INavComponent?,
        adoptingINavComponent: INavComponent?
    ): INavComponent? {

        if (adoptingINavComponent == donorNavComponent) {
            return adoptingINavComponent
        }

        val adoptingNavigatorCopy = adoptingINavComponent ?: return donorNavComponent

        return if (donorNavComponent == null) { // The first time when no node has been setup yet
            adoptingINavComponent.setItems(navItems, startingPosition)
            adoptingINavComponent
        } else { // do the real transfer here
            adoptingNavigatorCopy.transferFrom(donorNavComponent)
            adoptingNavigatorCopy
        }
    }

}