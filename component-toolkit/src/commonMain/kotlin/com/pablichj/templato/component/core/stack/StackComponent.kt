package com.pablichj.templato.component.core.stack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentLifecycleState
import com.pablichj.templato.component.core.ComponentWithBackStack
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.processBackstackEvent
import com.pablichj.templato.component.core.router.DeepLinkMatchData
import com.pablichj.templato.component.core.router.DeepLinkMatchType
import com.pablichj.templato.component.core.router.DeepLinkResult

abstract class StackComponent(
    protected val config: Config
) : Component(), ComponentWithBackStack {
    final override val backStack = BackStack<Component>()
    override var childComponents: MutableList<Component> = mutableListOf()
    var activeComponent: MutableState<Component?> = mutableStateOf(null)
    private var lastBackstackEvent: BackStack.Event<Component>? = null

    init {
        this@StackComponent.backStack.eventListener = { event ->
            lastBackstackEvent = event
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
    }

    override fun onStart() {
        println("$clazz::onStart()")
        if (activeComponent.value != null) {
            activeComponent.value?.dispatchStart()
        }
    }

    override fun onStop() {
        println("$clazz::onStop()")
        activeComponent.value?.dispatchStop()
        lastBackstackEvent = null
    }

    override fun handleBackPressed() {
        println("$clazz::handleBackPressed, backStack.size = ${backStack.size()}")
        if (backStack.size() > 1) {
            backStack.pop()
        } else {
            // We delegate the back event when the stack has 1 element and not 0. The reason is, if
            // we pop all the way to zero the stack empty view will be show for a fraction of
            // milliseconds and this creates an undesirable effect.
            delegateBackPressedToParent()
        }
    }

    // region: NavigatorItems

    override fun getComponent(): Component {
        return this
    }

    internal fun processBackstackTransition(
        stackTransition: StackTransition<Component>
    ) {
        when (stackTransition) {
            is StackTransition.In -> {
                onStackTopUpdate(stackTransition.newTop)
                activeComponent.value = stackTransition.newTop
            }

            is StackTransition.InOut -> {
                onStackTopUpdate(stackTransition.newTop)
                activeComponent.value = stackTransition.newTop
            }

            is StackTransition.InvalidPushEqualTop -> {}
            is StackTransition.InvalidPopEmptyStack -> {
                activeComponent.value = null
            }

            is StackTransition.Out -> {
                activeComponent.value = null
            }
        }
    }

    protected abstract fun onStackTopUpdate(topComponent: Component)

    override fun onDestroyChildComponent(component: Component) {
        if (component.lifecycleState == ComponentLifecycleState.Started) {
            component.dispatchStop()
            component.dispatchDestroy()
        } else {
            component.dispatchDestroy()
        }
    }

    // endregion

    // region: DeepLink

    override fun onDeepLinkNavigation(matchingComponent: Component): DeepLinkResult {
        println("$clazz.onDeepLinkMatch() matchingNode = ${matchingComponent.clazz}")
        backStack.push(matchingComponent)
        return DeepLinkResult.Success
    }

    override fun getDeepLinkHandler(): DeepLinkMatchData {
        return DeepLinkMatchData(
            null,
            DeepLinkMatchType.MatchAny
        )
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        childComponents.forEach {
            val linkHandler = it.getDeepLinkHandler()
            println("$clazz::child.uriFragment = ${linkHandler.uriFragment}")
            if (linkHandler.uriFragment == nextUriFragment) {
                return it
            }
            if (linkHandler.matchType == DeepLinkMatchType.MatchAny) {
                val childMatching = it.getChildForNextUriFragment(nextUriFragment)
                if (childMatching != null) {
                    return childMatching
                }
            }
        }
        return null
    }

    // endregion

    @Composable
    fun DefaultStackComponentView(
        modifier: Modifier,
        onComponentSwipedOut: () -> Unit
    ) {
        println(
            """
          $clazz::Composing(), backStack.size = ${backStack.size()}
          lastBackstackEvent = $lastBackstackEvent
        """
        )

        val animationType = when (lastBackstackEvent) {
            is BackStack.Event.Pop -> {
                if (backStack.size() > 0)
                    AnimationType.Reverse
                else AnimationType.Exit
            }

            is BackStack.Event.PopEmptyStack -> {
                AnimationType.Enter
            }

            is BackStack.Event.Push -> {
                if (backStack.size() > 1)
                    AnimationType.Direct
                else AnimationType.Enter
            }

            is BackStack.Event.PushEqualTop -> {
                AnimationType.Enter
            }

            null -> AnimationType.Enter
        }

        val prevComponent = if (backStack.size() > 1) {
            backStack.deque[backStack.size() - 2]
        } else {
            null
        }

        when (LocalBackPressedDispatcher.current.isSystemBackButtonEnabled()) {
            true -> {
                // If the traditional back button is enabled then we use our custom predictive back
                StackCustomPredictiveBack(
                    modifier = modifier,
                    childComponent = activeComponent.value,
                    prevChildComponent = prevComponent,
                    animationType = animationType,
                    onComponentSwipedOut = onComponentSwipedOut
                )
            }

            false -> {
                // Except Android, (and when the traditional 3 button navigation is enabled),
                // all the platforms will fall in to this case.
                StackSystemPredictiveBack(
                    modifier = modifier,
                    childComponent = activeComponent.value,
                    animationType = animationType
                )
            }
        }

    }

    class Config(
        val stackStyle: StackStyle = StackStyle(),
        val showBackArrowAlways: Boolean = true
    )

    companion object {
        val DefaultConfig = Config(
            StackStyle()
        )
    }

}

data class StackBarItem(
    val label: String,
    val icon: ImageVector,
)
