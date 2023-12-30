package com.macaosoftware.component.demo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.window.ComposeUIViewController
import com.macaosoftware.app.MacaoApplication
import com.macaosoftware.app.MacaoApplicationState
import com.macaosoftware.app.MacaoKoinApplication
import com.macaosoftware.app.MacaoKoinApplicationState
import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.RootComponentKoinProvider
import com.macaosoftware.app.RootComponentProvider
import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.plugin.DemoKoinModuleInitializer
import com.macaosoftware.component.demo.plugin.DemoPluginInitializer
import com.macaosoftware.component.demo.viewmodel.StackDemoViewModel
import com.macaosoftware.component.demo.viewmodel.factory.AdaptiveSizeDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.AppViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.DrawerDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.PagerDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.StackDemoViewModelFactory
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.pager.PagerComponent
import com.macaosoftware.component.pager.PagerComponentDefaults
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import com.macaosoftware.plugin.DefaultPlatformLifecyclePlugin
import com.macaosoftware.plugin.IOSBridge2
import com.macaosoftware.plugin.IosBridge
import com.macaosoftware.plugin.PlatformLifecyclePlugin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import platform.Foundation.NSURL
import platform.UIKit.UIViewController
import platform.posix.exit

fun buildDemoViewController(
    rootComponent: Component,
    iosBridge: IosBridge,
    iosBridge2: IOSBridge2 = IOSBridge2(test = NSURL(string = "https://google.com")),
    onBackPress: () -> Unit = {}
): UIViewController = ComposeUIViewController {

    val rootComponentProvider = object : RootComponentProvider {
        override suspend fun provideRootComponent(
            pluginManager: PluginManager
        ): Component {

            delay(2000)

            return StackComponent<StackDemoViewModel>(
                viewModelFactory = StackDemoViewModelFactory(
                    stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                    onBackPress = {
                        exit(0)
                        true
                    }
                ),
                content = StackComponentDefaults.DefaultStackComponentView
            )
        }

    }

    val macaoApplicationState = MacaoApplicationState(
        dispatcher = Dispatchers.IO,
        rootComponentProvider = rootComponentProvider,
        pluginInitializer = DemoPluginInitializer()
    )

    MacaoApplication(
        iosBridge = iosBridge,
        onBackPress = onBackPress,
        macaoApplicationState = macaoApplicationState,
        splashScreenContent = { SplashScreen() }
    )
}

fun buildKoinDemoViewController(
    rootComponent: Component,
    iosBridge: IosBridge,
    iosBridge2: IOSBridge2 = IOSBridge2(test = NSURL(string = "https://google.com")),
    onBackPress: () -> Unit = {}
): UIViewController = ComposeUIViewController {

    val rootComponentKoinProvider = object : RootComponentKoinProvider {
        override suspend fun provideRootComponent(
            koinComponent: KoinComponent
        ): Component {

            delay(2000)

            return StackComponent<StackDemoViewModel>(
                viewModelFactory = StackDemoViewModelFactory(
                    stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
                    onBackPress = {
                        exit(0)
                        true
                    }
                ),
                content = StackComponentDefaults.DefaultStackComponentView
            )
        }

    }

    val applicationState = MacaoKoinApplicationState(
        dispatcher = Dispatchers.IO,
        rootComponentKoinProvider = rootComponentKoinProvider,
        koinModuleInitializer = DemoKoinModuleInitializer()
    )

    MacaoKoinApplication(
        iosBridge = iosBridge,
        onBackPress = onBackPress,
        applicationState = applicationState,
        splashScreenContent = { SplashScreen() }
    )
}

fun buildDrawerComponent(): Component {
    return DrawerComponent(
        viewModelFactory = DrawerDemoViewModelFactory(
            DrawerComponentDefaults.createDrawerStatePresenter()
        ),
        content = DrawerComponentDefaults.DrawerComponentView
    )
}

@OptIn(ExperimentalFoundationApi::class)
fun buildPagerComponent(): Component {
    return PagerComponent(
        viewModelFactory = PagerDemoViewModelFactory(),
        content = PagerComponentDefaults.PagerComponentView
    )
}

fun buildAdaptableSizeComponent(): Component {
    return AdaptiveSizeComponent(AdaptiveSizeDemoViewModelFactory())
}

fun buildAppWithIntroComponent(): Component {
    return StackComponent(
        viewModelFactory = AppViewModelFactory(
            stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
        ),
        content = StackComponentDefaults.DefaultStackComponentView
    )
}

fun createDefaultPlatformLifecyclePlugin(): PlatformLifecyclePlugin {
    return DefaultPlatformLifecyclePlugin()
}
