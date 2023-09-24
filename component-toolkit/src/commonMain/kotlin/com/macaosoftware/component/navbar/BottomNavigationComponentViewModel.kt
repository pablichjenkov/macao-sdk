package com.macaosoftware.component.navbar

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.core.NavigationComponentDefaults
import com.macaosoftware.component.stack.AddAllPushStrategy
import com.macaosoftware.component.stack.PushStrategy
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.platform.CoroutineDispatchers

abstract class BottomNavigationComponentViewModel<T : NavBarStatePresenter>(
    protected val bottomNavigationComponent: NavigationComponent,
    private val lifecycleHandler: NavigationComponent.LifecycleHandler =
        NavigationComponentDefaults.createLifecycleHandler(),
    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy(),
) : ComponentViewModel(),
    NavigationComponent.LifecycleHandler by lifecycleHandler {

        abstract fun onCreate()
        abstract val bottomNavigationStatePresenter: T
    }

class BottomNavigationComponentDefaultViewModel(
    bottomNavigationComponent: NavigationComponent,
    override val bottomNavigationStatePresenter: NavBarStatePresenterDefault =
        NavBarComponentDefaults.createNavBarStatePresenter()
) : BottomNavigationComponentViewModel<NavBarStatePresenterDefault>(bottomNavigationComponent) {

    override fun onCreate() {
        println("BottomNavigationComponentDefaultViewModel::onCreate()")
    }

    override fun onStart() {
        println("BottomNavigationComponentDefaultViewModel::onStart()")
    }

    override fun onStop() {
        println("BottomNavigationComponentDefaultViewModel::onStop()")
    }

    override fun onDestroy() {
        println("BottomNavigationComponentDefaultViewModel::onDestroy()")
    }
}
