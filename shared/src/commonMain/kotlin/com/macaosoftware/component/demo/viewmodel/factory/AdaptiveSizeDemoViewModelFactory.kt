package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.adaptive.AdaptiveSizeComponent
import com.macaosoftware.component.adaptive.AdaptiveSizeComponentViewModelFactory
import com.macaosoftware.component.demo.viewmodel.AdaptiveSizeDemoViewModel
import com.macaosoftware.component.demo.viewmodel.DrawerDemoViewModel
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentViewModel
import com.macaosoftware.component.drawer.DrawerComponentViewModelFactory
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault

class AdaptiveSizeDemoViewModelFactory(
) : AdaptiveSizeComponentViewModelFactory {
    override fun create(adaptiveSizeComponent: AdaptiveSizeComponent): AdaptiveSizeDemoViewModel {
        return AdaptiveSizeDemoViewModel(adaptiveSizeComponent)
    }
}
