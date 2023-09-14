package com.macaosoftware.component.drawer

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.core.NavigationComponentDefaults
import com.macaosoftware.component.stack.AddAllPushStrategy
import com.macaosoftware.component.stack.PushStrategy
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.platform.CoroutineDispatchers

abstract class DrawerComponentViewModel<T : DrawerStatePresenter>(
    private val lifecycleHandler: NavigationComponent.LifecycleHandler =
        NavigationComponentDefaults.createLifecycleHandler(),
    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy(),
) : ComponentViewModel(),
    NavigationComponent.LifecycleHandler by lifecycleHandler {

    abstract fun create(drawerComponent: DrawerComponent<T>)
}

class DrawerComponentDefaultViewModel : DrawerComponentViewModel<DrawerStatePresenterDefault>() {

    override fun create(drawerComponent: DrawerComponent<DrawerStatePresenterDefault>) {
        println("DrawerComponentDefaultViewModel::create()")
    }

    override fun onStart() {
        println("DrawerComponentDefaultViewModel::onStart()")
    }

    override fun onStop() {
        println("DrawerComponentDefaultViewModel::onStop()")
    }

    override fun onDestroy() {
        println("DrawerComponentDefaultViewModel::onDestroy()")
    }
}
