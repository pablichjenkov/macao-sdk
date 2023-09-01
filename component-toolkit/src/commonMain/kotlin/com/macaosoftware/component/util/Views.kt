package com.macaosoftware.component.util

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.FloatingBackButton(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.TopStart,
    onClick: () -> Unit
) {
    FloatingActionButton(
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
