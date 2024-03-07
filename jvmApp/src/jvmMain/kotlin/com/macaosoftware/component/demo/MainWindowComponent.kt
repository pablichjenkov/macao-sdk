package com.macaosoftware.component.demo

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.app.MacaoKoinApplication
import com.macaosoftware.app.MacaoKoinApplicationState
import com.macaosoftware.app.KoinAppStage
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.DeepLinkMsg
import com.macaosoftware.component.core.deeplink.DefaultDeepLinkManager
import com.macaosoftware.component.demo.plugin.DemoKoinRootModuleInitializer
import com.macaosoftware.component.demo.viewmodel.factory.Demo3PageTopBarViewModelFactory
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import kotlinx.coroutines.Dispatchers
import kotlin.system.exitProcess

class MainWindowComponent(
    val onOpenDeepLinkClick: () -> Unit,
    val onMenuItemClick: (String) -> Unit,
    val onExitClick: () -> Unit
) : Component() {
    private val windowState = WindowState(size = DpSize(1000.dp, 900.dp))

    private val macaoKoinApplicationState = MacaoKoinApplicationState(
        dispatcher = Dispatchers.Default,
        rootComponentKoinProvider = JvmRootComponentKoinProvider(),
        koinRootModuleInitializer = DemoKoinRootModuleInitializer()
    )

    // region: DeepLink

    fun handleDeepLink(destinations: List<String>) {
        when (
            val applicationState = macaoKoinApplicationState.stage.value
        ) {
            KoinAppStage.Created,
            KoinAppStage.Loading -> {
                // no-op
            }

            is KoinAppStage.Started -> {
                val deepLinkMsg = DeepLinkMsg(
                    path = destinations,
                    resultListener = { result ->
                        println("MainWindowComponent::deepLinkResult = $result")
                    }
                )

                DefaultDeepLinkManager().navigateToDeepLink(applicationState.rootComponent, deepLinkMsg)
            }
        }
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onExitClick() }
        ) {
            DemoMenu(
                onOpenDeepLinkClick = onOpenDeepLinkClick,
                onMenuItemClick = onMenuItemClick,
                onExitClick = onExitClick
            )
            MacaoKoinApplication(
                windowState = windowState,
                onBackPress = { exitProcess(0) },
                applicationState = macaoKoinApplicationState
            )
        }
    }
}

@Composable
private fun FrameWindowScope.DemoMenu(
    onOpenDeepLinkClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    onExitClick: () -> Unit
) {
    MenuBar {
        Menu("Actions") {
            Item(
                "Deep Link",
                onClick = {
                    onOpenDeepLinkClick()
                }
            )
            Item(
                "Exit",
                onClick = {
                    onExitClick()
                }
            )
        }
        Menu("Samples") {
            Item(
                "Menu.Item.1",
                onClick = {
                    onMenuItemClick("Menu.Item.1")
                }
            )
            Item(
                "Menu.Item.2",
                onClick = {
                    onMenuItemClick("Menu.Item.2")
                }
            )
        }
    }
}

@Preview
@Composable
fun MainWindowComponentPreview() {

    /*
    val simpleComponent = SimpleComponent(
        "Simple",
        Color.Yellow,
    ) {}
    simpleComponent.Content(Modifier)
    */

    val topBarComponent = TopBarComponent(
        viewModelFactory = Demo3PageTopBarViewModelFactory(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
            screenName = "Orders",
            onDone = {}
        ),
        content = TopBarComponentDefaults.TopBarComponentView
    ).apply {
        deepLinkPathSegment = "Orders"
    }
    topBarComponent.dispatchActive()
    topBarComponent.Content(Modifier)
}
