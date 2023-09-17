package com.macaosoftware.component.pager

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.stack.AddAllPushStrategy
import com.macaosoftware.component.stack.PushStrategy
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.platform.CoroutineDispatchers

abstract class PagerComponentViewModel(
    val pagerStyle: PagerStyle = PagerStyle(),
    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy()
) : ComponentViewModel() {
    abstract fun onCreate(pagerComponent: PagerComponent)
}

class PagerComponentDefaultViewModel : PagerComponentViewModel() {
    private lateinit var pagerComponent: PagerComponent

    override fun onCreate(pagerComponent: PagerComponent) {
        this.pagerComponent = pagerComponent
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }
}
