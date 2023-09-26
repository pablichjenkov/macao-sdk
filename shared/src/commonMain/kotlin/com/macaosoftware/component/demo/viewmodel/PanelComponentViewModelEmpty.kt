package com.macaosoftware.component.demo.viewmodel

import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.component.panel.PanelComponentViewModel
import com.macaosoftware.component.panel.PanelStatePresenterDefault

class PanelComponentViewModelEmpty(
    panelComponent: PanelComponent<PanelComponentViewModelEmpty>,
    override val panelStatePresenter: PanelStatePresenterDefault =
        PanelComponentDefaults.createPanelStatePresenter()
) : PanelComponentViewModel(panelComponent) {

    override fun onCreate() {
        println("PanelComponentViewModelEmpty::create()")
    }

    override fun onStart() {
        println("PanelComponentViewModelEmpty::create()")
    }

    override fun onStop() {
        println("PanelComponentViewModelEmpty::create()")
    }

    override fun onDestroy() {
        println("PanelComponentViewModelEmpty::create()")
    }
}
