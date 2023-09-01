package com.pablichj.templato.component.demo.componentDelegates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDelegate
import com.macaosoftware.component.topbar.TopBarItem
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault
import com.pablichj.templato.component.demo.SimpleComponent

class TopBarComponentDelegate1(
    screenName: String,
    onDone: () -> Unit
) : TopBarComponentDelegate<TopBarStatePresenterDefault>() {

    var topBarComponent: TopBarComponent<TopBarStatePresenterDefault>? = null

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

    override fun TopBarComponent<TopBarStatePresenterDefault>.create() {
        topBarComponent = this
        listOf(Step1, Step2, Step3).forEach {
            it.setParent(this)
        }
    }

    override fun TopBarComponent<TopBarStatePresenterDefault>.start() {
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

    override fun TopBarComponent<TopBarStatePresenterDefault>.stop() {
    }

    override fun TopBarComponent<TopBarStatePresenterDefault>.destroy() {
        println("${instanceId()}::onStop()")
    }

    override fun mapComponentToStackBarItem(topComponent: Component): TopBarItem {
        return when (topComponent) {
            Step1 -> {
                TopBarItem(
                    Step1.screenName,
                    Icons.Filled.Star,
                )
            }

            Step2 -> {
                TopBarItem(
                    Step2.screenName,
                    Icons.Filled.Star,
                )
            }

            Step3 -> {
                TopBarItem(
                    Step3.screenName,
                    Icons.Filled.Star,
                )
            }

            else -> {
                throw IllegalStateException()
            }
        }
    }

    override fun TopBarComponent<TopBarStatePresenterDefault>.componentDelegateChildForNextUriFragment(nextUriFragment: String): Component? {
        println("${instanceId()}::getChildForNextUriFragment = $nextUriFragment")
        return when (nextUriFragment) {
            Step1.uriFragment -> Step1
            Step2.uriFragment -> Step2
            Step3.uriFragment -> Step3
            else -> null
        }
    }

    companion object {
        fun create(
            screenName: String,
            onDone: () -> Unit
        ) : TopBarComponentDelegate1 {
            return TopBarComponentDelegate1(screenName, onDone)
        }
    }
}
