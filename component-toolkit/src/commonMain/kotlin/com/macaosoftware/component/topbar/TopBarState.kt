package com.macaosoftware.component.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.macaosoftware.component.drawer.DrawerNavigationProvider
import com.macaosoftware.component.drawer.EmptyDrawerNavigationProvider

data class TopBarState(
    val title: String? = null,
    val onTitleClick: ((DrawerNavigationProvider) -> Unit)? = null,
    val onIconGlobalNavigationClick: ((DrawerNavigationProvider) -> Unit)? = null,
    val backNavigationIcon: ImageVector? = null,
    val onBackNavigationIconClick: ((DrawerNavigationProvider) -> Unit)? = null
) {
    fun resolveGlobalNavigationIcon1(
        drawerNavigationProvider: DrawerNavigationProvider
    ): ImageVector? {
        return if (drawerNavigationProvider is EmptyDrawerNavigationProvider) {
            null
        } else {
            Icons.Filled.Menu
        }
    }
}
