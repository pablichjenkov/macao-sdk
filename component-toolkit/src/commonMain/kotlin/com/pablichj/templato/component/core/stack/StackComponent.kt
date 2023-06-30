package com.pablichj.templato.component.core.stack

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.router.DeepLinkResult
import com.pablichj.templato.component.core.*
import com.pablichj.templato.component.core.processBackstackEvent
import com.pablichj.templato.component.core.router.DeepLinkMatchData
import com.pablichj.templato.component.core.router.DeepLinkMatchType

abstract class StackComponent(
    private val config: Config
) : Component(), ComponentWithBackStack {
    final override val backStack = BackStack<Component>()
    override var childComponents: MutableList<Component> = mutableListOf()
    var activeComponent: MutableState<Component?> = mutableStateOf(null)

    private var topBarState = TopBarState {
        handleBackPressed()
    }

    private var lastBackstackEvent: BackStack.Event<Component>? = null

    init {
        this@StackComponent.backStack.eventListener = { event ->
            lastBackstackEvent = event
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
    }

    override fun onStart() {
        if (activeComponent.value != null) {
            println("$clazz::start() with activeNodeState = ${activeComponent.value?.clazz}")
            activeComponent.value?.dispatchStart()
        }
    }

    override fun onStop() {
        println("$clazz::stop()")
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
                updateSelectedComponent(stackTransition.newTop)
                activeComponent.value = stackTransition.newTop
            }
            is StackTransition.InOut -> {
                updateSelectedComponent(stackTransition.newTop)
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

    protected open fun updateSelectedComponent(newTop: Component) {
        val selectedStackBarItem = getStackBarItemFromComponent(newTop) ?: return
        if (backStack.size() > 1) {
            setTitleSectionForBackClick(selectedStackBarItem)
        } else {
            setTitleSectionForHomeClick(selectedStackBarItem)
        }
    }

    abstract fun getStackBarItemFromComponent(component: Component): StackBarItem?

    override fun onDestroyChildComponent(component: Component) {
        if (component.lifecycleState == ComponentLifecycleState.Started) {
            component.dispatchStop()
            component.dispatchDestroy()
        } else {
            component.dispatchDestroy()
        }
    }

    // endregion

    protected fun setTitleSectionForHomeClick(stackBarItem: StackBarItem) {
        topBarState = TopBarState(
            onBackPress = { handleBackPressed() }
        ).apply {
            setTitleSectionState(
                TitleSectionStateHolder(
                    title = stackBarItem.label,
                    icon1 = resolveFirstIcon(),
                    onIcon1Click = {
                        findClosestDrawerNavigationComponent()?.open()
                    },
                    onTitleClick = {
                        findClosestDrawerNavigationComponent()?.open()
                    }
                )
            )
        }
    }

    protected fun setTitleSectionForBackClick(stackBarItem: StackBarItem) {
        topBarState = TopBarState {
            handleBackPressed()
        }.apply {
            setTitleSectionState(
                TitleSectionStateHolder(
                    title = stackBarItem.label,
                    onTitleClick = {
                        handleBackPressed()
                    },
                    icon1 = resolveFirstIcon(),
                    onIcon1Click = {
                        findClosestDrawerNavigationComponent()?.open()
                    },
                    icon2 = Icons.Filled.ArrowBack,
                    onIcon2Click = {
                        handleBackPressed()
                    }
                )
            )
        }
    }

    private fun resolveFirstIcon(): ImageVector? {
        val canProvideGlobalNavigation = findClosestDrawerNavigationComponent() != null
        return if (canProvideGlobalNavigation) {
            Icons.Filled.Menu
        } else {
            null
        }
    }

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
            println("NavBar::child.uriFragment = ${linkHandler.uriFragment}")
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
    override fun Content(modifier: Modifier) {
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
                    modifier,
                    topBarState,
                    activeComponent.value,
                    prevComponent,
                    animationType
                )
            }
            false -> {
                // Except Android, (and when the traditional 3 button navigation is enabled),
                // all the platforms will fall in to this case.
                StackSystemPredictiveBack(
                    modifier,
                    topBarState,
                    activeComponent.value,
                    animationType
                )
            }
        }

    }

    class Config(
        stackStyle: StackStyle = StackStyle()
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
