package com.macaosoftware.component.pager

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.stack.AddAllPushStrategy
import com.macaosoftware.component.stack.PushStrategy
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.platform.CoroutineDispatchers

abstract class PagerComponentViewModel(
    val pagerComponent: PagerComponent,
    val pagerStyle: PagerStyle = PagerStyle(),
    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy()
) : ComponentViewModel() {

    abstract fun onCreate()
}
