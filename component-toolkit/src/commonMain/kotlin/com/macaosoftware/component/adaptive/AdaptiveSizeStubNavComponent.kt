package com.macaosoftware.component.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.stack.BackStack

internal class AdaptiveSizeStubNavComponent : Component(), NavigationComponent {
    override val backStack: BackStack<Component> = BackStack()
    override var selectedIndex: Int = 0
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(this)

    override fun onStart() {
        println("${instanceId()}.start()")
    }

    override fun onStop() {
        println("${instanceId()}.stop()")
    }

    override fun getComponent(): Component {
        return activeComponent.value ?: this
    }

    override fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>) {
        this.selectedIndex = selectedIndex
    }

    override fun updateSelectedNavItem(newTop: Component) {
    }

    override fun onDestroyChildComponent(component: Component) {
        component.dispatchDestroy()
    }

    @Composable
    override fun Content(modifier: Modifier) {
    }

}