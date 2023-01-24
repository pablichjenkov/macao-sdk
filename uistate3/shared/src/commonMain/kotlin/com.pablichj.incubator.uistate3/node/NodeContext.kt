package com.pablichj.incubator.uistate3.node

import com.pablichj.incubator.uistate3.node.navigation.SubPath

/**
 * Singly Linked List that represents a path of NodeContext from the current node
 * up to the root node.
 * */
open class NodeContext {

    var parentContext: NodeContext? = null

    // region: Tree traversal

    fun attachToParent(parentContext: NodeContext) {
        this.parentContext = parentContext
    }

    fun isAttached(): Boolean = parentContext != null

    // endregion

    // region: LifecycleState

    var lifecycleState: Node.LifecycleState = Node.LifecycleState.Created

    // endregion

    // region: INavigationProvider

    private var navigationProvider: INavigationProvider? = null

    fun setNavigationProvider(navigationProvider: INavigationProvider) {
        this.navigationProvider = navigationProvider
    }

    fun findClosestNavigationProvider(): INavigationProvider? {
        // Check if this Node itself contains the property we are looking for
        if (navigationProvider != null) return navigationProvider

        var contextIterator = parentContext

        while (contextIterator != null) {

            val navigationProvider = contextIterator.navigationProvider

            if (navigationProvider != null) {
                return navigationProvider
            }

            contextIterator = contextIterator.parentContext
        }

        return null
    }

    // endregion

    // region: IBackPressDispatcher

    internal var backPressedCallbackDelegate: BackPressedCallback = EmptyBackPressCallback
    internal var rootNodeBackPressedDelegate : BackPressedCallback? = null

    // endregion

    // region: Deep Link

    var subPath: SubPath = SubPath.Empty

    // endregion

}