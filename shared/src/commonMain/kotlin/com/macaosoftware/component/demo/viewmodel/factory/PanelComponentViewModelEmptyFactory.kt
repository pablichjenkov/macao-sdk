package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.PanelComponentViewModelEmpty
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentViewModel
import com.macaosoftware.component.panel.PanelComponentViewModelFactory
import com.macaosoftware.component.panel.PanelStatePresenterDefault

class PanelComponentViewModelEmptyFactory : PanelComponentViewModelFactory<PanelStatePresenterDefault> {

    override fun create(
        panelComponent: PanelComponent<PanelStatePresenterDefault>
    ): PanelComponentViewModel<PanelStatePresenterDefault> {
        return PanelComponentViewModelEmpty(panelComponent)
    }
}
