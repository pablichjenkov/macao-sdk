package com.pablichj.incubator.uistate3.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.backstack.BackStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AppCoordinatorComponent : Component() {
    val backStack = BackStack<Component>()

    private val splashComponent = SplashComponent {
        backStack.push(homeComponent)
    }.also { it.setParent(this@AppCoordinatorComponent) }

    //todo: Use setHomeNode instead, and attach to parent context, see SplitNode class as example
    lateinit var homeComponent: SimpleComponent

    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin
    private var activeComponent: MutableState<Component?> = mutableStateOf(null)

    override fun start() {
        super.start()
        println("AppCoordinatorComponent::start()")
        backStack.eventListener = { event ->
            processBackstackEvent(event)
        }
        if (activeComponent.value == null) {
            backStack.push(splashComponent)
        } else {
            activeComponent.value?.start()
        }
    }

    override fun stop() {
        super.stop()
        println("AppCoordinatorComponent::stop()")
        backStack.eventListener = { }
        activeComponent.value?.stop()
    }

    /**
     * This class override the default handleBackPressed() behavior in BackStackNode
     * */
    override fun handleBackPressed() {

        // TODO: Replace this logic by a proper state machine state variable and not the class type
        when (val node = activeComponent.value) {
            splashComponent -> {

            }
            else -> {
                delegateBackPressedToParent()
            }
        }
    }

    fun processBackstackEvent(event: BackStack.Event<Component>) {
        when (event) {
            is BackStack.Event.Push -> {
                val stack = event.stack
                val newTop = stack[stack.lastIndex]
                val oldTop = stack.getOrNull(stack.lastIndex - 1)
                println(
                    "AppCoordinatorComponent::Event.StackPush()," +
                            " oldTop: ${oldTop?.let { it::class.simpleName }}," +
                            " newTop: ${newTop::class.simpleName}"
                )

                newTop.start()
                oldTop?.stop()
                activeComponent.value = newTop
                //updateSelectedNavItem(newTop)
            }
            is BackStack.Event.Pop -> {
                val stack = event.stack
                val newTop = stack.getOrNull(stack.lastIndex)
                val oldTop = event.oldTop
                println(
                    "AppCoordinatorComponent::Event.StackPop(), " +
                            "oldTop: ${oldTop::class.simpleName}," +
                            " newTop: ${newTop?.let { it::class.simpleName }}"
                )

                activeComponent.value = newTop
                newTop?.start()
                oldTop.stop()
                //newTop?.let { updateSelectedNavItem(it) }
            }
            is BackStack.Event.PushEqualTop -> {
                println(
                    "AppCoordinatorComponent::Event.PushEqualTop()," +
                            " backStack.size = ${backStack.size()}"
                )
            }
            is BackStack.Event.PopEmptyStack -> {
                println("AppCoordinatorComponent::Event.PopEmptyStack(), backStack.size = 0")
            }
        }
    }

    @Composable
    /*override */ internal fun Content(modifier: Modifier) {
        println(
            "AppCoordinatorNode::Composing() stack.size = ${backStack.size()}"
        )

        Box(modifier = Modifier.fillMaxSize()) {
            val activeComponentCopy = activeComponent.value
            if (activeComponentCopy != null && backStack.size() > 0) {
                //activeComponentCopy.Content(Modifier)

                when (activeComponentCopy) {
                    splashComponent -> {
                        splashComponent.Content(Modifier)
                    }
                    homeComponent -> {
                        homeComponent.Content(Modifier)
                    }
                }

            } else {
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

}