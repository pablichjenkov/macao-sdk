package com.pablichj.templato.component.core.panel

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun PanelHeader(
    modifier: Modifier = Modifier,
    panelHeaderState: PanelHeaderState
) {
    when(panelHeaderState) {
        NoPanelHeaderState -> {
            // No Op
        }
        is PanelHeaderStateDefault -> {
            DefaultPanelHeader(modifier, panelHeaderState)
        }
    }
}

@Composable
private fun DefaultPanelHeader(
    modifier: Modifier = Modifier,
    panelHeaderState: PanelHeaderStateDefault
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.LightGray)
            .padding(all = 16.dp),
    ) {
        Column(modifier = modifier) {
            Text(
                text = panelHeaderState.title,
                fontSize = panelHeaderState.style.titleTextSize
            )
            Spacer(
                modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            Text(
                text = panelHeaderState.description,
                fontSize = panelHeaderState.style.descriptionTextSize
            )
        }
    }
}
