package com.macaosoftware.component.stack

interface StackComponentViewModelFactory<T : StackStatePresenter> {
    fun create(stackComponent: StackComponent<T>): StackComponentViewModel<T>
}