package com.pablichj.templato.component.core.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavigationComponent
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.stack.BackStack

internal class AdaptiveSizeStubNavComponent : Component(), NavigationComponent {
    override val backStack: BackStack<Component> = BackStack()
    override var selectedIndex: Int = 0
    override var navItems: MutableList<NavItem> = mutableListOf()
    override var childComponents: MutableList<Component> = mutableListOf()
    override var activeComponent: MutableState<Component?> = mutableStateOf(this)

    override fun start() {
        super.start()
        println("$clazz.start()")
    }

    override fun stop() {
        super.stop()
        println("$clazz.stop()")
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
        component.destroy()
    }

    @Composable
    override fun Content(modifier: Modifier) {
    }

}