package com.pablichj.templato.component.core.util

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.ComponentDefaults
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun EmptyNavigationComponentView(
    component: Component
) {
    var show by remember { mutableStateOf(false) }
    LaunchedEffect(component) {
        delay(1.seconds)
        show = true
    }
    if (show) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center).padding(16.dp),
                text = "${component.instanceId()}\n${ComponentDefaults.EmptyStackMessage}"
            )
        }
    }
}
