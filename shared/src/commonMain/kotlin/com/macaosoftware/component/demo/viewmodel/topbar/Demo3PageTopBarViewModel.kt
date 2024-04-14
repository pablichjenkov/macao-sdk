package com.macaosoftware.component.demo.viewmodel.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.noviewmodel.SimpleComponent
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModel
import com.macaosoftware.component.topbar.TopBarItem
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

class Demo3PageTopBarViewModel(
    topBarComponent: TopBarComponent<Demo3PageTopBarViewModel>,
    override val topBarStatePresenter: TopBarStatePresenterDefault,
    screenName: String,
    onDone: () -> Unit,
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
        it.deepLinkPathSegment = "Page 2"
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
        it.deepLinkPathSegment = "Page 3"
    }

    override fun onAttach() {
        println("${topBarComponent.instanceId()}::Demo3PageTopBarViewModel::onAttach()")
        listOf(Step1, Step2, Step3).forEach {
            it.setParent(topBarComponent)
        }
    }

    override fun onStart() {
        println("${topBarComponent.instanceId()}::Demo3PageTopBarViewModel::onStart()")

        val navigator = topBarComponent.navigator
        val topComponent = navigator.top()
        if (topComponent == null
            || topBarComponent.backstackInfo.isTopComponentStaled
        ) {
            topBarComponent.navigator.replaceTop(Step1)
            topBarComponent.lastBackstackEvent = null
            return
        }
    }

    override fun onStop() {
        println("${topBarComponent.instanceId()}::Demo3PageTopBarViewModel::onStop()")
    }

    override fun onDetach() {
        println("${topBarComponent.instanceId()}::Demo3PageTopBarViewModel::onDetach()")
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
        println("Demo3PageTopBarViewModel::ChildForNextUriFragment nextUriFragment = $deepLinkPathSegment")
        return when (deepLinkPathSegment) {
            Step1.deepLinkPathSegment -> Step1
            Step2.deepLinkPathSegment -> Step2
            Step3.deepLinkPathSegment -> Step3
            else -> null
        }
    }

}
