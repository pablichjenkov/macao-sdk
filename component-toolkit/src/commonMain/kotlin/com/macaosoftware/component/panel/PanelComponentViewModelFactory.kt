package com.macaosoftware.component.panel

interface PanelComponentViewModelFactory<VM : PanelComponentViewModel> {
    fun create(panelComponent: PanelComponent<VM>): VM
}
