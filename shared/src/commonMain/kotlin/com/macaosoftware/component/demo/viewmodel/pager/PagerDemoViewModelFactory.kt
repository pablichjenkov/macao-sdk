package com.macaosoftware.component.demo.viewmodel.pager

import com.macaosoftware.component.demo.viewmodel.pager.PagerDemoViewModel
import com.macaosoftware.component.pager.PagerComponent
import com.macaosoftware.component.pager.PagerComponentViewModelFactory

class PagerDemoViewModelFactory : PagerComponentViewModelFactory<PagerDemoViewModel> {
    override fun create(
        pagerComponent: PagerComponent<PagerDemoViewModel>
    ): PagerDemoViewModel {
        return PagerDemoViewModel(pagerComponent)
    }
}
