package com.macaosoftware.component.demo.startup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.macaosoftware.component.viewmodel.StateComponent

val StartupView: @Composable StateComponent<StartupViewModel>.(
    modifier: Modifier,
    componentViewModel: StartupViewModel
) -> Unit = { modifier: Modifier,
              startupViewModel: StartupViewModel ->

    val timeLeft by startupViewModel.splashTimeFlow.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = "Splash done in: $timeLeft seconds",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
    }

}