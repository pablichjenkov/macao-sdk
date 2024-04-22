package com.macaosoftware.component.demo.viewmodel.adaptive

import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.adaptive.AdaptiveSizeComponentViewModelFactory
import com.macaosoftware.component.demo.viewmodel.adaptive.AdaptiveSizeDemoViewModel

class AdaptiveSizeDemoViewModelFactory(
) : AdaptiveSizeComponentViewModelFactory<AdaptiveSizeDemoViewModel> {
    override fun create(
        adaptiveSizeComponent: AdaptiveSizeComponent<AdaptiveSizeDemoViewModel>
    ): AdaptiveSizeDemoViewModel {
        return AdaptiveSizeDemoViewModel(adaptiveSizeComponent)
    }
}
