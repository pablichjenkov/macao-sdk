package com.macaosoftware.component.navbar

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.core.NavigationComponentDefaults
import com.macaosoftware.component.stack.AddAllPushStrategy
import com.macaosoftware.component.stack.PushStrategy
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.platform.CoroutineDispatchers

abstract class NavBarComponentViewModel<T : NavBarStatePresenter>(
    private val lifecycleHandler: NavigationComponent.LifecycleHandler =
        NavigationComponentDefaults.createLifecycleHandler(),
    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy(),
) : ComponentViewModel(),
    NavigationComponent.LifecycleHandler by lifecycleHandler {

    abstract fun onCreate(navBarComponent: NavBarComponent<T>)
}

class NavBarComponentDefaultViewModel : NavBarComponentViewModel<NavBarStatePresenterDefault>() {

    override fun onCreate(navBarComponent: NavBarComponent<NavBarStatePresenterDefault>) {
        println("NavBarComponentDefaultViewModel::create()")
    }

    override fun onStart() {
        println("NavBarComponentDefaultViewModel::onStart()")
    }

    override fun onStop() {
        println("NavBarComponentDefaultViewModel::onStop()")
    }

    override fun onDestroy() {
        println("NavBarComponentDefaultViewModel::onDestroy()")
    }
}
