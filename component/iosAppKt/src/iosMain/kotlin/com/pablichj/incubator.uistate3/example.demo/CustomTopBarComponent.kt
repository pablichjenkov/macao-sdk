package com.pablichj.incubator.uistate3.example.demo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.topbar.TopBarComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class CustomTopBarComponent(
    val screenName: String,
    screenIcon: ImageVector? = null,
    val onMessage: (Msg) -> Unit
) : TopBarComponent(screenIcon) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin

    val Step1 = SimpleComponent(
        "$screenName / Page 1",
        Color.Yellow
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                backStack.push(Step2)
            }
        }
    }.also {
        it.setParent(this@CustomTopBarComponent)
    }

    val Step2 = SimpleComponent(
        "$screenName / Page 1 / Page 2",
        Color.Green
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                backStack.push(Step3)
            }
        }
    }.also {
        it.setParent(this@CustomTopBarComponent)
    }

    val Step3 =
        SimpleComponent(
            "$screenName / Page 1 / Page 2 / Page 3",
            Color.Cyan
        ) { msg ->
            when (msg) {
                SimpleComponent.Msg.Next -> {
                    onMessage(Msg.OnboardDone)
                    treeContext?.navigator?.handleDeepLink("Settings")
                }
            }
        }.also {
            it.setParent(this@CustomTopBarComponent)
        }

    init {
        childComponents = mutableListOf(Step1, Step2, Step3)
        navItems = mutableListOf(
            NavItem(
                Step1.text,
                Icons.Filled.Star,
                Step1
            ),
            NavItem(
                Step2.text,
                Icons.Filled.Star,
                Step2
            ),
            NavItem(
                Step3.text,
                Icons.Filled.Star,
                Step3
            ),
        )
    }

    override fun start() {
        //super.start() We override the default NavComponent behavior in purpose
        println("CustomTopBarComponent::start()")
        if (activeComponent.value == null) {
            backStack.push(Step1)
        } else {
            activeComponent.value?.start()
        }
    }

    // region: DeepLink

    override fun getDeepLinkSubscribedList(): List<Component> {
        return listOf(Step1, Step2, Step3)
    }

    // endregion

    sealed interface Msg {
        object OnboardDone : Msg
    }

}