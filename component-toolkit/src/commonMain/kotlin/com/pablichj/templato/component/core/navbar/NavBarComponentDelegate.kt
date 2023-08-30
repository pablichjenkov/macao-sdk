package com.pablichj.templato.component.core.navbar

import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.NavigationComponentDefaults
import com.pablichj.templato.component.core.stack.AddAllPushStrategy
import com.pablichj.templato.component.core.stack.PushStrategy
import com.pablichj.templato.component.platform.CoroutineDispatchers

abstract class NavBarComponentDelegate<T : NavBarStatePresenter>(
    private val lifecycleHandler: NavigationComponent.LifecycleHandler =
        NavigationComponentDefaults.createLifecycleHandler()
) : NavigationComponent.LifecycleHandler by lifecycleHandler {

    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults
    open val pushStrategy: PushStrategy<Component> = AddAllPushStrategy()
    abstract fun mapComponentToNavBarNavItem(component: Component): NavBarNavItem
}
