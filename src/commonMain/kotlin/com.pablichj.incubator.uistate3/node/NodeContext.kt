package com.pablichj.incubator.uistate3.node

import com.pablichj.incubator.uistate3.node.navigation.SubPath

/**
 * Singly Linked List that represents a path of NodeContext from the current node
 * up to the root node.
 * */
open class NodeContext(
    var parentContext: NodeContext?
) {

    // region: Tree traversal

    fun updateParent(newParentContext: NodeContext) {
        parentContext = newParentContext
    }

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

    //todo: make these 2 internal
    var backPressDispatcher: IBackPressDispatcher? = null
    var backPressedCallbackDelegate: BackPressedCallback = EmptyBackPressCallback

    // endregion

    // region: Deep Link

    var subPath: SubPath = SubPath.Empty

    // endregion

    class Root : NodeContext(null)

}