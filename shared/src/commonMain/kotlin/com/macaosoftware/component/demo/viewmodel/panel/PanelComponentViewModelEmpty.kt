package com.macaosoftware.component.demo.viewmodel.panel

import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.component.panel.PanelComponentViewModel
import com.macaosoftware.component.panel.PanelStatePresenterDefault

class PanelComponentViewModelEmpty(
    panelComponent: PanelComponent<PanelComponentViewModelEmpty>,
    override val panelStatePresenter: PanelStatePresenterDefault =
        PanelComponentDefaults.createPanelStatePresenter()
) : PanelComponentViewModel(panelComponent) {

    override fun onAttach() {
        println("PanelComponentViewModelEmpty::onAttach()")
    }

    override fun onStart() {
        println("PanelComponentViewModelEmpty::onStart()")
    }

    override fun onStop() {
        println("PanelComponentViewModelEmpty::onStop()")
    }

    override fun onDetach() {
        println("PanelComponentViewModelEmpty::onDetach()")
    }
}
