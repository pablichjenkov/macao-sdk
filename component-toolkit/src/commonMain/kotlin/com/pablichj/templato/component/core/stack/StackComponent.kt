package com.pablichj.templato.component.core.stack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentWithBackStack
import com.pablichj.templato.component.core.componentWithBackStackGetChildForNextUriFragment
import com.pablichj.templato.component.core.componentWithBackStackOnDeepLinkNavigateTo
import com.pablichj.templato.component.core.consumeBackPressedDefault
import com.pablichj.templato.component.core.deeplink.DeepLinkResult
import com.pablichj.templato.component.core.destroyChildComponent
import com.pablichj.templato.component.core.processBackstackEvent
import com.pablichj.templato.component.core.util.EmptyNavigationComponentView

class StackComponent<T : StackStatePresenter>(
    val stackStatePresenter: T,
    private val componentDelegate: StackComponentDelegate<T>,
    private val content: @Composable StackComponent<T>.(
        modifier: Modifier,
        activeComponent: Component
    ) -> Unit
) : Component(), ComponentWithBackStack {

    override val backStack = BackStack<Component>()
    override var childComponents: MutableList<Component> = mutableListOf()
    var activeComponent: MutableState<Component?> = mutableStateOf(null)
    var lastBackstackEvent: BackStack.Event<Component>? = null

    init {
        this@StackComponent.backStack.eventListener = { event ->
            lastBackstackEvent = event
            val stackTransition = processBackstackEvent(event)
            processBackstackTransition(stackTransition)
        }
    }

    override fun onStart() {
        if (activeComponent.value != null) {
            activeComponent.value?.dispatchStart()
        }
    }

    override fun onStop() {
        activeComponent.value?.dispatchStop()
        lastBackstackEvent = null
    }

    override fun handleBackPressed() {
        println("${instanceId()}::handleBackPressed, backStack.size = ${backStack.size()}")
        if (consumeBackPressedDefault().not()) {
            // We delegate the back event when the stack has 1 element and not 0. The reason is, if
            // we pop all the way to zero the stack empty view will be show for a fraction of
            // milliseconds and this creates an undesirable effect.
            delegateBackPressedToParent()
        }
    }

// region: ComponentWithChildren

    override fun getComponent(): Component {
        return this
    }

    private fun processBackstackTransition(
        stackTransition: StackTransition<Component>
    ) {
        when (stackTransition) {
            is StackTransition.In -> {
                dispatchStackTopUpdate(stackTransition.newTop)
                activeComponent.value = stackTransition.newTop
            }

            is StackTransition.InOut -> {
                dispatchStackTopUpdate(stackTransition.newTop)
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

    private fun dispatchStackTopUpdate(topComponent: Component) {
        componentDelegate.onStackTopUpdate(topComponent)
    }

    override fun onDestroyChildComponent(component: Component) {
        destroyChildComponent()
    }

// endregion

// region: DeepLink

    override fun onDeepLinkNavigateTo(matchingComponent: Component): DeepLinkResult {
        return (this as ComponentWithBackStack).componentWithBackStackOnDeepLinkNavigateTo(matchingComponent)
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return (this as ComponentWithBackStack).componentWithBackStackGetChildForNextUriFragment(nextUriFragment)
    }

// endregion

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """${instanceId()}.Composing() stack.size = ${backStack.size()}
                |lifecycleState = ${lifecycleState}
            """
        )
        val activeComponentCopy = activeComponent.value
        if (activeComponentCopy != null) {
            content(modifier, activeComponentCopy)
        } else {
            EmptyNavigationComponentView(this@StackComponent)
        }
    }

}
