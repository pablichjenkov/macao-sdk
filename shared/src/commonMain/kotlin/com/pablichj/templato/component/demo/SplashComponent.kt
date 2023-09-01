package com.pablichj.templato.component.demo

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
import com.macaosoftware.component.core.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashComponent(
    val onDone: () -> Unit
) : Component() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var splashJob: Job? = null
    private val SplashDelaySeconds = 3
    private val splashTimeFlow = MutableStateFlow(SplashDelaySeconds)

    override fun onStart() {
        splashJob = coroutineScope.launch {
            var timeLeft = SplashDelaySeconds
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
                splashTimeFlow.value = timeLeft
            }
            onDone()
            splashJob?.cancel()
        }
    }

    override fun onStop() {
        splashJob?.cancel()
    }

    @Composable
    override fun Content(modifier: Modifier) {

        val timeLeft by splashTimeFlow.collectAsState()

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

}