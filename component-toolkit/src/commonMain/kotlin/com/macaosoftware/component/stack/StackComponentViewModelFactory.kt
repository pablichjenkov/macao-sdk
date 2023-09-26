package com.macaosoftware.component.stack

interface StackComponentViewModelFactory<VM : StackComponentViewModel> {
    fun create(stackComponent: StackComponent<VM>): StackComponentViewModel
}