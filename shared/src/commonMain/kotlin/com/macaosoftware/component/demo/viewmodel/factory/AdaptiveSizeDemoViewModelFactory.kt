package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.adaptive.AdaptiveSizeComponentViewModelFactory
import com.macaosoftware.component.demo.viewmodel.AdaptiveSizeDemoViewModel

class AdaptiveSizeDemoViewModelFactory(
) : AdaptiveSizeComponentViewModelFactory<AdaptiveSizeDemoViewModel> {
    override fun create(
        adaptiveSizeComponent: AdaptiveSizeComponent<AdaptiveSizeDemoViewModel>
    ): AdaptiveSizeDemoViewModel {
        return AdaptiveSizeDemoViewModel(adaptiveSizeComponent)
    }
}
