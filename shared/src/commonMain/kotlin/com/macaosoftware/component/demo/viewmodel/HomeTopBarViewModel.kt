package com.macaosoftware.component.demo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.clearBackStack
import com.macaosoftware.component.core.push
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

    private val homeComponent = topBarComponent
    private var currentComponent: Component? = null

    val Step1 = SimpleComponent(
        "$screenName/Page 1",
        Color.Yellow
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                homeComponent.navigator.push(Step2)
            }
        }
    }.also {
        it.setParent(homeComponent)
        it.uriFragment = "Page 1"
    }

    val Step2 = SimpleComponent(
        "$screenName/Page 2",
        Color.Green
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                homeComponent.navigator.push(Step3)
            }
        }
    }.also {
        it.setParent(homeComponent)
        it.uriFragment = "Page 2"
    }

    val Step3 =
        SimpleRequestComponent(
            "$screenName/Page 3",
            Color.Cyan
        ).also {
            it.setParent(homeComponent)
            it.uriFragment = "Page 3"
        }

    override fun onAttach() {
        println("${topBarComponent.instanceId()}::HomeTopBarViewModel::onAttach()")
    }

    override fun onStart() {
        println("${topBarComponent.instanceId()}::HomeTopBarViewModel::onStart()")
        val shouldPushFirstChild: Boolean = if (currentComponent == null) {
            true
        } else if (topBarComponent.isFirstComponentInStackPreviousCache) {
            currentComponent != Step1
        } else false

        if (shouldPushFirstChild) {
            currentComponent = Step1
            topBarComponent.navigator.clearBackStack()
            topBarComponent.navigator.push(Step1)
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

    override fun onCheckChildForNextUriFragment(nextUriFragment: String): Component? {
        println("${homeComponent.instanceId()}::getChildForNextUriFragment = $nextUriFragment")
        return when (nextUriFragment) {
            Step1.uriFragment -> Step1
            Step2.uriFragment -> Step2
            Step3.uriFragment -> Step3
            else -> null
        }
    }

}
