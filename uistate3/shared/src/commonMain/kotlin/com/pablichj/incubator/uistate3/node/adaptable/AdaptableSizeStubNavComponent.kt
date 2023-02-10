package com.pablichj.incubator.uistate3.node.adaptable

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.NavComponent
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.backstack.BackStack

internal class AdaptableSizeStubNavComponent : NavComponent {
    override val backStack: BackStack<Component> = BackStack()
    override var selectedIndex: Int = 0
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var childComponents: MutableList<Component> = mutableListOf()
    private val adaptableSizeStubComponent = AdaptableSizeStubComponent()
    override var activeComponent: MutableState<Component?> = mutableStateOf(adaptableSizeStubComponent)

    override fun getComponent(): Component {
        return activeComponent.value ?: adaptableSizeStubComponent
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        this.selectedIndex = selectedIndex
    }

    override fun updateSelectedNavItem(newTop: Component) {
    }

    override fun onDestroyChildComponent(component: Component) {
        component.destroy()
    }

}