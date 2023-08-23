package com.pablichj.templato.component.demo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.core.topbar.TopBarStatePresenterDefault

/*
class CustomTopBarComponent(
    val screenName: String,
    val onMessage: (Msg) -> Unit
) : TopBarComponent<TopBarStatePresenterDefault>(
    topBarStatePresenter = createDefaultTopBarStatePresenter(),
    content = DefaultTopBarComponentView
) {

    val Step1 = SimpleComponent(
        "$screenName/Page 1",
        Color.Yellow
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                backStack.push(Step2)
            }
        }
    }.also {
        it.setParent(this@CustomTopBarComponent)
        it.uriFragment = "Page 1"
    }

    val Step2 = SimpleComponent(
        "$screenName/Page 2",
        Color.Green
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                backStack.push(Step3)
            }
        }
    }.also {
        it.setParent(this@CustomTopBarComponent)
        it.uriFragment = "Page 2"
    }

    val Step3 = SimpleComponent(
        "$screenName/Page 3",
        Color.Cyan
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                onMessage(Msg.OnboardDone)
            }
        }
    }.also {
        it.setParent(this@CustomTopBarComponent)
        it.uriFragment = "Page 3"
    }

    override fun onStart() {
        println("${instanceId()}::onStart()")
        if (activeComponent.value == null) {
            if (getComponent().startedFromDeepLink) {
                return
            }
            backStack.push(Step1)
        } else {
            activeComponent.value?.dispatchStart()
        }
    }

    override fun onStop() {
        println("${instanceId()}::onStop()")
    }

    fun getStackBarItemForComponent(topComponent: Component): StackBarItem {
        return when (topComponent) {
            Step1 -> {
                StackBarItem(
                    Step1.screenName,
                    Icons.Filled.Star,
                )
            }

            Step2 -> {
                StackBarItem(
                    Step2.screenName,
                    Icons.Filled.Star,
                )
            }

            Step3 -> {
                StackBarItem(
                    Step3.screenName,
                    Icons.Filled.Star,
                )
            }

            else -> {
                throw IllegalStateException()
            }
        }
    }

    // region: DeepLink

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        println("${instanceId()}::getChildForNextUriFragment = $nextUriFragment")
        return when (nextUriFragment) {
            Step1.uriFragment -> Step1
            Step2.uriFragment -> Step2
            Step3.uriFragment -> Step3
            else -> null
        }
    }

    // endregion

    sealed interface Msg {
        object OnboardDone : Msg
    }

}
*/