package com.macaosoftware.component

import androidx.compose.ui.uikit.ComposeUIViewControllerDelegate
import com.macaosoftware.plugin.AppLifecycleEvent
import com.macaosoftware.plugin.PlatformLifecyclePlugin

class MacaoComposeUIViewControllerDelegate(
    private val platformLifecyclePlugin: PlatformLifecyclePlugin
) : ComposeUIViewControllerDelegate {

    override fun viewWillAppear(animated: Boolean) {
        println("MacaoComposeUIViewControllerDelegate::viewWillAppear")
    }

    override fun viewDidAppear(animated: Boolean) {
        println("MacaoComposeUIViewControllerDelegate::viewDidAppear")
        platformLifecyclePlugin.dispatchAppLifecycleEvent(AppLifecycleEvent.Start)
    }

    override fun viewWillDisappear(animated: Boolean) {
        println("MacaoComposeUIViewControllerDelegate::viewDidAppear")
    }

    override fun viewDidDisappear(animated: Boolean) {
        println("MacaoComposeUIViewControllerDelegate::viewDidDisappear")
        platformLifecyclePlugin.dispatchAppLifecycleEvent(AppLifecycleEvent.Stop)
    }
}
