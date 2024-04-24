package com.macaosoftware.component.pager

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.stack.AddAllPushStrategy
import com.macaosoftware.component.stack.PushStrategy
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.plugin.CoroutineDispatchers

abstract class PagerComponentViewModel(
    val pagerComponent: PagerComponent<PagerComponentViewModel>,
    val pagerStyle: PagerStyle = PagerStyle(),
    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Default,
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy()
) : ComponentViewModel()
