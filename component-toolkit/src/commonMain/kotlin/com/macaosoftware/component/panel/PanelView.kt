package com.macaosoftware.component.panel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavigationPanel(
    modifier: Modifier = Modifier,
    panelStatePresenter: PanelStatePresenter,
    Content: @Composable () -> Unit
) {
    val navItems by panelStatePresenter.navItemsState
    val panelHeaderState by panelStatePresenter.panelHeaderState
    val panelStyle = panelStatePresenter.panelStyle

    Row(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.width(280.dp)
        ) {
            PanelHeader(panelHeaderState = panelHeaderState)
            PanelContentList(
                navItems = navItems,
                panelStyle = panelStyle,
                onNavItemClick = { navItem -> panelStatePresenter.navItemClick(navItem) }
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Content()
        }
    }

}

@Composable
private fun PanelContentList(
    modifier: Modifier = Modifier,
    navItems: List<PanelNavItem>,
    panelStyle: PanelStyle,
    onNavItemClick: (PanelNavItem) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = panelStyle.bgColor)
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = panelStyle.horizontalAlignment
    ) {
        for (navItem in navItems) {
            PanelDrawerItem(
                panelStyle = panelStyle,
                label = {
                    Text(
                        text = navItem.label,
                        fontSize = panelStyle.itemTextSize
                    )
                },
                icon = { Icon(navItem.icon, null) },
                selected = navItem.selected,
                onClick = { onNavItemClick(navItem) }
            )
        }
    }
}

@Composable
private fun PanelDrawerItem(
    panelStyle: PanelStyle,
    label: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit
) {
    val modifier = if (selected) {
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(width = 1.dp, color = panelStyle.borderColor)
            .background(panelStyle.selectedColor)
            .padding(8.dp)
            .clickable {
                onClick()
            }
    } else {
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(8.dp)
            .clickable {
                onClick()
            }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        icon()
        Spacer(Modifier.width(8.dp))
        label()
    }
}
