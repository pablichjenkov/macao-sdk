package com.macaosoftware.component.demo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.SimpleComponent
import com.macaosoftware.component.demo.SimpleRequestComponent
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModel
import com.macaosoftware.component.topbar.TopBarItem
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

class HomeTopBarViewModel(
    topBarComponent: TopBarComponent<HomeTopBarViewModel>,
    override val topBarStatePresenter: TopBarStatePresenterDefault,
    screenName: String,
    onDone: () -> Unit
) : TopBarComponentViewModel(topBarComponent) {

    val Step1 = SimpleComponent(
        "$screenName/Page 1",
        Color.Yellow
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                topBarComponent.navigator.push(Step2)
            }
        }
    }.also {
        it.setParent(topBarComponent)
        it.deepLinkPathSegment = "Page 1"
    }

    val Step2 = SimpleComponent(
        "$screenName/Page 2",
        Color.Green
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                topBarComponent.navigator.push(Step3)
            }
        }
    }.also {
        it.setParent(topBarComponent)
        it.deepLinkPathSegment = "Page 2"
    }

    val Step3 =
        SimpleRequestComponent(
            "$screenName/Page 3",
            Color.Cyan
        ).also {
            it.setParent(topBarComponent)
            it.deepLinkPathSegment = "Page 3"
        }

    override fun onAttach() {
        println("${topBarComponent.instanceId()}::HomeTopBarViewModel::onAttach()")
    }

    override fun onStart() {
        println("${topBarComponent.instanceId()}::HomeTopBarViewModel::onStart()")

        val navigator = topBarComponent.navigator
        val topComponent = navigator.top()
        if (topComponent == null) {
            topBarComponent.navigator.push(Step1)
            return
        }
        val stackSize = navigator.stackSize()
        if (stackSize == 1 && topComponent != Step1) {
            topBarComponent.navigator.replaceTop(Step1)
            topBarComponent.lastBackstackEvent = null
            return
        }
    }

    override fun onStop() {
        println("${topBarComponent.instanceId()}::HomeTopBarViewModel::onStop()")
    }

    override fun onDetach() {
        println("${topBarComponent.instanceId()}::HomeTopBarViewModel::onDetach()")
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

    override fun onCheckChildForNextUriFragment(deepLinkPathSegment: String): Component? {
        println("${topBarComponent.instanceId()}::getChildForNextUriFragment = $deepLinkPathSegment")
        return when (deepLinkPathSegment) {
            Step1.deepLinkPathSegment -> Step1
            Step2.deepLinkPathSegment -> Step2
            Step3.deepLinkPathSegment -> Step3
            else -> null
        }
    }

}
