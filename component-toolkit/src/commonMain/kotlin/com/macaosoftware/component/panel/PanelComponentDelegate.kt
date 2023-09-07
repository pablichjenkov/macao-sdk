package com.macaosoftware.component.panel

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.core.NavigationComponentDefaults
import com.macaosoftware.component.stack.AddAllPushStrategy
import com.macaosoftware.component.stack.PushStrategy
import com.macaosoftware.platform.CoroutineDispatchers

abstract class PanelComponentDelegate<T: PanelStatePresenter>(
    private val lifecycleHandler: NavigationComponent.LifecycleHandler =
        NavigationComponentDefaults.createLifecycleHandler(),
    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy(),
) : NavigationComponent.LifecycleHandler by lifecycleHandler
