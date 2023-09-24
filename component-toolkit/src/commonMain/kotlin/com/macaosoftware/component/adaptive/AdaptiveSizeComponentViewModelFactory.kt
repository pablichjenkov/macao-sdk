package com.macaosoftware.component.adaptive

interface AdaptiveSizeComponentViewModelFactory {
    fun create(adaptiveSizeComponent: AdaptiveSizeComponent): AdaptiveSizeComponentViewModel
}

class AdaptiveSizeComponentViewModelFactoryDefault: AdaptiveSizeComponentViewModelFactory {

    override fun create(adaptiveSizeComponent: AdaptiveSizeComponent): AdaptiveSizeComponentViewModel {
        return AdaptiveSizeComponentDefaultViewModel()
    }
}
