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
import com.pablichj.incubator.uistate3.node.Container
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
    private var CompactNavigator: Container? = null
    private var MediumNavigator: Container? = null
    private var ExpandedNavigator: Container? = null
    private var currentContainer =
        mutableStateOf<Container?>(null) // todo: This should be a reactive state

    fun setNavItems(navItems: MutableList<NodeItem>, startingPosition: Int) {
        this.navItems = navItems
        this.startingPosition = startingPosition
        currentContainer.value?.setItems(navItems, startingPosition)
    }

    fun setCompactContainer(container: Container) {
        CompactNavigator = container
        container.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    fun setMediumContainer(container: Container) {
        MediumNavigator = container
        container.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    fun setExpandedContainer(container: Container) {
        ExpandedNavigator = container
        container.getComponent().attachToParent(this@AdaptableSizeComponent)
    }

    override fun start() {
        super.start()
        currentContainer.value?.getComponent()?.start()
    }

    override fun stop() {
        super.stop()
        currentContainer.value?.getComponent()?.stop()
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
        val interceptingNode = currentContainer.value?.getComponent() ?: matchingComponent
        interceptingNode.subPath = matchingComponent.subPath.copy()
        return interceptingNode.checkDeepLinkMatch(advancedPath)
    }

    override fun onNavigateChildMatchHandler(
        advancedPath: Path,
        matchingComponent: Component
    ): DeepLinkResult {
        val interceptingNode = currentContainer.value?.getComponent() ?: matchingComponent
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

        currentContainer.value = when (windowSizeInfo) {
            WindowSizeInfo.Compact -> {
                tryTransfer(currentContainer.value, CompactNavigator)
            }
            WindowSizeInfo.Medium -> {
                tryTransfer(currentContainer.value, MediumNavigator)
            }
            WindowSizeInfo.Expanded -> {
                tryTransfer(currentContainer.value, ExpandedNavigator)
            }
        }

        val CurrentNode = currentContainer.value?.getComponent()

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
        donorContainer: Container?,
        adoptingContainer: Container?
    ): Container? {

        if (adoptingContainer == donorContainer) {
            return adoptingContainer
        }

        val adoptingNavigatorCopy = adoptingContainer ?: return donorContainer

        return if (donorContainer == null) { // The first time when no node has been setup yet
            adoptingContainer.setItems(navItems, startingPosition)
            adoptingContainer.getComponent().start()
            adoptingContainer
        } else { // do the real transfer here
            adoptingNavigatorCopy.transferFrom(donorContainer)
            donorContainer.getComponent().stop()
            adoptingContainer.getComponent().start()
            adoptingContainer
        }
    }

}