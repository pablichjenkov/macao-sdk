package com.pablichj.templato.component.demo

import androidx.compose.ui.graphics.Color
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.stack.BackStack
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.core.topbar.TopBarComponentDelegate
import com.pablichj.templato.component.core.topbar.TopBarStatePresenterDefault

fun createCustomTopBarComponent (
    screenName: String,
    onDone: () -> Unit
) : TopBarComponent<TopBarStatePresenterDefault> {

    val componentDelegate: TopBarComponentDelegate = object: TopBarComponentDelegate {

        var topBarComponent: TopBarComponent<*>? = null

        val Step1 = SimpleComponent(
            "$screenName/Page 1",
            Color.Yellow
        ) { msg ->
            when (msg) {
                SimpleComponent.Msg.Next -> {
                    topBarComponent?.backStack?.push(Step2)
                }
            }
        }.also {
            it.uriFragment = "Page 1"
        }

        val Step2 = SimpleComponent(
            "$screenName/Page 2",
            Color.Green
        ) { msg ->
            when (msg) {
                SimpleComponent.Msg.Next -> {
                    topBarComponent?.backStack?.push(Step3)
                }
            }
        }.also {
            it.uriFragment = "Page 2"
        }

        val Step3 = SimpleComponent(
            "$screenName/Page 3",
            Color.Cyan
        ) { msg ->
            when (msg) {
                SimpleComponent.Msg.Next -> {
                    onDone()
                }
            }
        }.also {
            it.uriFragment = "Page 3"
        }

        override fun TopBarComponent<*>.create() {
            topBarComponent = this
            listOf(Step1, Step2, Step3).forEach {
                it.setParent(this)
            }
        }

        override fun TopBarComponent<*>.start() {
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

        override fun TopBarComponent<*>.stop() {
            println("${instanceId()}::onStop()")
        }

        override fun TopBarComponent<*>.childForNextUriFragment(nextUriFragment: String): Component? {
            println("${instanceId()}::getChildForNextUriFragment = $nextUriFragment")
            return when (nextUriFragment) {
                Step1.uriFragment -> Step1
                Step2.uriFragment -> Step2
                Step3.uriFragment -> Step3
                else -> null
            }
        }
    }

    val topBarComponent = TopBarComponent(
        topBarStatePresenter = TopBarComponent.createDefaultTopBarStatePresenter(),
        componentDelegate = componentDelegate,
        content = TopBarComponent.DefaultTopBarComponentView
    )

    return topBarComponent
}