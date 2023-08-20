package com.pablichj.templato.component.demo.viewmodel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val SimpleViewModelView: @Composable (SimpleViewModel) -> Unit = { viewModel ->
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(12.dp))
        Text(
            text = viewModel.text,
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = {
                viewModel.next()
            }
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
