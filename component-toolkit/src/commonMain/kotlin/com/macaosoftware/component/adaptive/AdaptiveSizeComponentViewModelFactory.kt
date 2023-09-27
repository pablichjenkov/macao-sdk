package com.macaosoftware.component.adaptive

interface AdaptiveSizeComponentViewModelFactory<VM : AdaptiveSizeComponentViewModel> {
    fun create(adaptiveSizeComponent: AdaptiveSizeComponent<VM>): VM
}
