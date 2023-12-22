package com.macaosoftware.component.demo

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.app.JvmMacaoApplication
import com.macaosoftware.app.MacaoApplicationState
import com.macaosoftware.app.RootComponentProvider
import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.DeepLinkMsg
import com.macaosoftware.component.core.deeplink.DefaultDeepLinkManager
import com.macaosoftware.component.demo.viewmodel.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.factory.AdaptiveSizeDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.Demo3PageTopBarViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.StackDemoViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import com.macaosoftware.plugin.DesktopBridge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlin.system.exitProcess

class MainWindowComponent(
    val onOpenDeepLinkClick: () -> Unit,
    val onMenuItemClick: (WindowSample) -> Unit,
    val onExitClick: () -> Unit
) : Component() {
    private val windowState = WindowState(size = DpSize(1000.dp, 900.dp))
    private var adaptableSizeComponent = AdaptiveSizeComponent(AdaptiveSizeDemoViewModelFactory())
    private val desktopBridge = DesktopBridge()

    // region: DeepLink

    fun handleDeepLink(destinations: List<String>) {
        val deepLinkMsg = DeepLinkMsg(
            path = destinations,
            resultListener = { result ->
                println("MainWindowNode::deepLinkResult = $result")
            }
        )
        DefaultDeepLinkManager().navigateToDeepLink(adaptableSizeComponent, deepLinkMsg)
    }

    // endregion

    private val rootComponentProvider = object : RootComponentProvider {
        override suspend fun provideRootComponent(): Component {

            delay(2000)

            return StackComponent<StackDemoViewModel>(
                viewModelFactory = StackDemoViewModelFactory(
                    stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                    onBackPress = {
                        exitProcess(0)
                    }
                ),
                content = StackComponentDefaults.DefaultStackComponentView
            )
        }
    }

    private val macaoApplicationState = MacaoApplicationState(
        Dispatchers.Default,
        rootComponentProvider
    )

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
            JvmMacaoApplication(
                windowState = windowState,
                desktopBridge = desktopBridge,
                onBackPress = { exitProcess(0) },
                macaoApplicationState = macaoApplicationState
            ) {
                SplashScreen()
            }
        }
    }
}

@Composable
private fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Example of JVM Splash Screen"
        )
    }
}

@Composable
private fun FrameWindowScope.DemoMenu(
    onOpenDeepLinkClick: () -> Unit,
    onMenuItemClick: (WindowSample) -> Unit,
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
                "Slide Drawer",
                onClick = {
                    onMenuItemClick(WindowSample.Drawer)
                }
            )
            Item(
                "Nav Bottom Bar",
                onClick = {
                    onMenuItemClick(WindowSample.Navbar)
                }
            )
            Item(
                "Left Panel",
                onClick = {
                    onMenuItemClick(WindowSample.Panel)
                }
            )
            Item(
                "Full App Sample",
                onClick = {
                    onMenuItemClick(WindowSample.FullApp)
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

    val topbarComponent = TopBarComponent(
        viewModelFactory = Demo3PageTopBarViewModelFactory(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
            screenName = "Orders",
            onDone = {}
        ),
        content = TopBarComponentDefaults.TopBarComponentView
    ).apply {
        uriFragment = "Orders"
    }
    topbarComponent.dispatchStart()
    topbarComponent.Content(Modifier)
}
