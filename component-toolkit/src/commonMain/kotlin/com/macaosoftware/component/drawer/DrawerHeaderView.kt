package com.macaosoftware.component.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
internal fun DrawerHeader(
    modifier: Modifier = Modifier,
    drawerHeaderState: DrawerHeaderState
) {
    when (drawerHeaderState) {
        NoDrawerHeaderState -> {
            // No Op
        }

        is DrawerHeaderDefaultState -> DefaultDrawerHeader(modifier, drawerHeaderState)
    }
}

@Composable
private fun DefaultDrawerHeader(
    modifier: Modifier = Modifier,
    drawerHeaderState: DrawerHeaderDefaultState
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(drawerHeaderState.style.bgColor)
            .padding(all = 16.dp),
    ) {
        Column(modifier = modifier) {
            Text(
                text = drawerHeaderState.title,
                fontSize = drawerHeaderState.style.titleTextSize,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Text(
                text = drawerHeaderState.description,
                fontSize = drawerHeaderState.style.descriptionTextSize
            )
        }
    }
}
