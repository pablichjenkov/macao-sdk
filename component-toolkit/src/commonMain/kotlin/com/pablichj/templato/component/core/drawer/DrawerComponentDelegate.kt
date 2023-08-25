package com.pablichj.templato.component.core.drawer

import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.NavigationComponentDefaultLifecycleHandler
import com.pablichj.templato.component.core.stack.AddAllPushStrategy
import com.pablichj.templato.component.core.stack.PushStrategy
import com.pablichj.templato.component.platform.CoroutineDispatchers

abstract class DrawerComponentDelegate<T : DrawerStatePresenter>(
    private val lifecycleHandler: NavigationComponent.LifecycleHandler = NavigationComponentDefaultLifecycleHandler()
) : NavigationComponent.LifecycleHandler by lifecycleHandler {

    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults
    open val pushStrategy: PushStrategy<Component> = AddAllPushStrategy()
    abstract fun mapComponentToDrawerNavItem(component: Component): DrawerNavItem
}
