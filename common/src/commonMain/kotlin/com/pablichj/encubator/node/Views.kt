package com.pablichj.encubator.node

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.FloatingButton(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = modifier
            .align(alignment)
            .padding(8.dp),
        onClick = {
            onClick()
        }
    ) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Navigate Back")
    }
}
