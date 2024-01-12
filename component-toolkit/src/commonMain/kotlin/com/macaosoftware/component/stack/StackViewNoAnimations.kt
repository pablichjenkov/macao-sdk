package com.macaosoftware.component.stack

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.macaosoftware.component.core.Component

@Composable
fun StackViewNoAnimations(
    modifier: Modifier,
    childComponent: Component?,
    animationType: AnimationType
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (childComponent != null) {
            childComponent.Content(Modifier)
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = "StackSystemPredictiveBack Empty Stack, Please add some children",
                textAlign = TextAlign.Center
            )
        }
    }
}
