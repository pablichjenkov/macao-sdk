package com.pablichj.incubator.uistate3.node

interface ParentComponent {
    fun getComponent(): Component
    var childComponents: MutableList<Component>
    fun onDestroyChildComponent(component: Component)
}