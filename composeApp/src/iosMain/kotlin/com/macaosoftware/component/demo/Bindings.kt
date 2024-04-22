package com.macaosoftware.component.demo

import com.macaosoftware.plugin.app.MacaoComposeViewController
import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.plugin.DemoPluginInitializer
import com.macaosoftware.component.demo.viewmodel.adaptive.AdaptiveSizeDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.drawer.DrawerDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.pager.PagerDemoViewModelFactory
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.pager.PagerComponent
import com.macaosoftware.component.pager.PagerComponentDefaults
import com.macaosoftware.plugin.app.MacaoApplicationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.UIKit.UIViewController

fun buildDemoViewController(): UIViewController {

    val applicationState = MacaoApplicationState(
        dispatcher = Dispatchers.IO,
        rootComponentProvider = IosRootComponentProvider(),
        pluginInitializer = DemoPluginInitializer()
    )

    return MacaoComposeViewController(
        applicationState = applicationState
    )
}

private fun buildDrawerComponent(): Component {
    return DrawerComponent(
        viewModelFactory = DrawerDemoViewModelFactory(
            DrawerComponentDefaults.createDrawerStatePresenter()
        ),
        content = DrawerComponentDefaults.DrawerComponentView
    )
}

private fun buildPagerComponent(): Component {
    return PagerComponent(
        viewModelFactory = PagerDemoViewModelFactory(),
        content = PagerComponentDefaults.PagerComponentView
    )
}

private fun buildAdaptableSizeComponent(): Component {
    return AdaptiveSizeComponent(AdaptiveSizeDemoViewModelFactory())
}
