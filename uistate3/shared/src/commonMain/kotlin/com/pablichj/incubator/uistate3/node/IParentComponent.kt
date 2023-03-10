package com.pablichj.incubator.uistate3.node

interface IParentComponent {
    fun getComponent(): Component
    var childComponents: MutableList<Component>
    fun onDestroyChildComponent(component: Component)
}