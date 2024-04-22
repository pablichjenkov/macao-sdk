package com.macaosoftware.component.demo.viewmodel.panel

import com.macaosoftware.component.demo.viewmodel.panel.PanelDemoViewModel
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentViewModelFactory
import com.macaosoftware.component.panel.PanelStatePresenterDefault

class PanelDemoViewModelFactory(
    private val panelStatePresenter: PanelStatePresenterDefault
) : PanelComponentViewModelFactory<PanelDemoViewModel> {
    override fun create(
        panelComponent: PanelComponent<PanelDemoViewModel>
    ): PanelDemoViewModel {
        return PanelDemoViewModel(panelComponent, panelStatePresenter)
    }
}
