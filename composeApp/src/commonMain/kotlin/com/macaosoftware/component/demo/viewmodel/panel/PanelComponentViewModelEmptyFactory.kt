package com.macaosoftware.component.demo.viewmodel.panel

import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentViewModelFactory
import com.macaosoftware.component.panel.PanelStatePresenterDefault

class PanelComponentViewModelEmptyFactory(
    private val panelStatePresenter: PanelStatePresenterDefault
) : PanelComponentViewModelFactory<PanelComponentViewModelEmpty> {

    override fun create(
        panelComponent: PanelComponent<PanelComponentViewModelEmpty>
    ): PanelComponentViewModelEmpty {
        return PanelComponentViewModelEmpty(
            panelComponent = panelComponent,
            panelStatePresenter = panelStatePresenter
        )
    }
}
