package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.PagerDemoViewModel
import com.macaosoftware.component.pager.PagerComponent
import com.macaosoftware.component.pager.PagerComponentViewModel
import com.macaosoftware.component.pager.PagerComponentViewModelFactory

class PagerDemoViewModelFactory : PagerComponentViewModelFactory {
    override fun create(pagerComponent: PagerComponent): PagerComponentViewModel {
        return PagerDemoViewModel(pagerComponent)
    }
}
