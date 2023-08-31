package com.pablichj.templato.component.core.panel

import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.NavigationComponentDefaults
import com.pablichj.templato.component.core.stack.AddAllPushStrategy
import com.pablichj.templato.component.core.stack.PushStrategy
import com.pablichj.templato.component.platform.CoroutineDispatchers

abstract class PanelComponentDelegate<T: PanelStatePresenter>(
    private val lifecycleHandler: NavigationComponent.LifecycleHandler =
        NavigationComponentDefaults.createLifecycleHandler()
) : NavigationComponent.LifecycleHandler by lifecycleHandler {

    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults
    open val pushStrategy: PushStrategy<Component> = AddAllPushStrategy()
}
