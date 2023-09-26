package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.PanelComponentViewModelEmpty
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentViewModelFactory

class PanelComponentViewModelEmptyFactory : PanelComponentViewModelFactory<PanelComponentViewModelEmpty> {

    override fun create(
        panelComponent: PanelComponent<PanelComponentViewModelEmpty>
    ): PanelComponentViewModelEmpty {
        return PanelComponentViewModelEmpty(panelComponent)
    }
}
