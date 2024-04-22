package com.macaosoftware.component.demo.startup

import com.macaosoftware.component.viewmodel.ComponentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StartupViewModel : ComponentViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var splashJob: Job? = null
    private val SplashDelaySeconds = 3
    val splashTimeFlow = MutableStateFlow(SplashDelaySeconds)

    override fun onAttach() {
        splashJob = coroutineScope.launch {
            var timeLeft = SplashDelaySeconds
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
                splashTimeFlow.value = timeLeft
            }

            splashJob?.cancel()
        }
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDetach() {
        splashJob?.cancel()
    }
}
