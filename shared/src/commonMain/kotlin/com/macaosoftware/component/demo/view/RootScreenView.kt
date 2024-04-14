package com.macaosoftware.component.demo.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.macaosoftware.component.core.BackPressHandler
import com.macaosoftware.component.demo.viewmodel.root.RootScreenViewModel
import com.macaosoftware.component.viewmodel.StateComponent

val RootScreenView: @Composable StateComponent<RootScreenViewModel>.(
    modifier: Modifier,
    componentViewModel: RootScreenViewModel
) -> Unit = { modifier, mainScreenViewModel ->

    BackPressHandler()
    DemoSelection(
        modifier = modifier,
        onClick = {
            mainScreenViewModel.onClick(it)
        }
    )
}

@Composable
private fun DemoSelection(
    modifier: Modifier,
    onClick: (DemoType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LaunchButton(
            "Drawer Example"
        ) {
            onClick(DemoType.drawer)
        }
        LaunchButton(
            "Pager Example"
        ) {
            onClick(DemoType.pager)
        }
        LaunchButton(
            "Panel Example"
        ) {
            onClick(DemoType.panel)
        }
        LaunchButton(
            "BottomNavigation Example"
        ) {
            onClick(DemoType.bottomNavigation)
        }
        LaunchButton(
            "Adaptive Navigation Example"
        ) {
            onClick(DemoType.adaptive)
        }
    }
}

@Composable
private fun LaunchButton(
    text: String,
    onClick: () -> Unit
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    )
    Button(onClick = onClick) {
        Text(text)
    }
}

enum class DemoType {
    bottomNavigation,
    drawer,
    pager,
    panel,
    adaptive
}
