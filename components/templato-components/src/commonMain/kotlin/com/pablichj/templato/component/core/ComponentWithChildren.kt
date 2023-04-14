package com.pablichj.templato.component.core

interface ComponentWithChildren {
    fun getComponent(): Component
    var childComponents: MutableList<Component>
    fun onDestroyChildComponent(component: Component)
}