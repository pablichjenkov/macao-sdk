package com.macaosoftware.component.viewmodel

interface ComponentViewModelFactory<VM : ComponentViewModel> {
    fun create(component: StateComponent<VM>): VM
}