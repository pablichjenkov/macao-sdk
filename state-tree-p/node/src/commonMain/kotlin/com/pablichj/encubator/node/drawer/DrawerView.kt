package com.pablichj.encubator.node.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pablichj.encubator.node.NavigatorNodeItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    navDrawerState: INavigationDrawerState,
    Content: @Composable () -> Unit
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            DrawerContentModal(modifier, navDrawerState)
        },
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = true,
        scrimColor = DrawerDefaults.scrimColor
    ) {
        Scaffold { paddingValues ->
            Box(modifier = modifier.fillMaxSize().padding(paddingValues)) {
                Content()
            }
        }
    }

    LaunchedEffect(key1 = navDrawerState) {
        navDrawerState.drawerOpenFlow.collect { drawerValue ->
            when (drawerValue) {
                DrawerValue.Closed -> drawerState.close()
                DrawerValue.Open -> drawerState.open()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContentModal(
    modifier: Modifier = Modifier,
    navDrawerState: INavigationDrawerState
) {
    val navItems by navDrawerState.navItemsFlow.collectAsState(emptyList())

    ModalDrawerSheet(modifier = modifier) {
        DrawerLogo()
        DrawerContentList(
            navItems = navItems,
            onNavItemClick = { navItem -> navDrawerState.navItemClick(navItem) }
        )
    }
}

@Composable
fun DrawerLogo(
    // TODO: Create the Header State in INavigationDrawerState and use it here
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center,
    ) {
        Row(modifier = modifier) {
            Text(text = "Drawer Header")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContentList(
    modifier: Modifier = Modifier,
    navItems: List<NavigatorNodeItem>,
    onNavItemClick: (NavigatorNodeItem) -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (navItem in navItems) {
            NavigationDrawerItem(
                label = { Text(navItem.label) },
                icon = { Icon(navItem.icon, null) },
                selected = navItem.selected,
                onClick = { onNavItemClick(navItem) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}