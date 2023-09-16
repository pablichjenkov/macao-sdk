package com.macaosoftware.component.panel

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.core.NavigationComponentDefaults
import com.macaosoftware.component.stack.AddAllPushStrategy
import com.macaosoftware.component.stack.PushStrategy
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.platform.CoroutineDispatchers

abstract class PanelComponentViewModel<T : PanelStatePresenter>(
    private val lifecycleHandler: NavigationComponent.LifecycleHandler =
        NavigationComponentDefaults.createLifecycleHandler(),
    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy(),
) : ComponentViewModel(),
    NavigationComponent.LifecycleHandler by lifecycleHandler {

    abstract fun onCreate(panelComponent: PanelComponent<T>)
}

class PanelComponentDefaultViewModel : PanelComponentViewModel<PanelStatePresenterDefault>() {

    override fun onCreate(panelComponent: PanelComponent<PanelStatePresenterDefault>) {
        println("PanelComponentDefaultViewModel::create()")
    }

    override fun onStart() {
        println("PanelComponentDefaultViewModel::create()")
    }

    override fun onStop() {
        println("PanelComponentDefaultViewModel::create()")
    }

    override fun onDestroy() {
        println("PanelComponentDefaultViewModel::create()")
    }
}
