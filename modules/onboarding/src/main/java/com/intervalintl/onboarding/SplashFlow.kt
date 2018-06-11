package com.intervalintl.onboarding

import android.util.Log
import com.intervalintl.common.StateContext
import com.intervalintl.workflow.Flow
import com.intervalintl.common.Constants
import com.intervalintl.common.FlowViewPortService
import com.intervalintl.workflow.view.FlowViewPort


class SplashFlow(viewModelId: String) : Flow<StateContext, SplashFlow.SplashEvent>(viewModelId) {

    enum class Stage {
        Idle,
        Splash,
        Done
    }

    var stage = Stage.Idle
        private set

    private var flowViewPort: FlowViewPort? = null

    // TODO: Use PublishSubject from RxJava
    private var listener: Listener? = null


    override fun onStateContextUpdate(stateContext: StateContext) {

        flowViewPort = stateContext
                .getState(FlowViewPortService::class.java, Constants.DEFAULT_FLOW_VIEWPORT_SERVICE_ID)
                ?.getFlowViewPort()
    }

    override fun start() {

        if (stage == Stage.Idle) {
            showSplashScreen()
        } else if (stage == Stage.Done) {
            Log.d("SplashViewModel", "Calling start but SplashViewModel is done.")
        }
    }

    override fun stop() {

    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    private fun showSplashScreen() {
        val splashFragment = SplashFragment()
        splashFragment.setFlowId(flowId)

        flowViewPort?.setView(splashFragment, Constants.SPLASH_FRAGMENT_TAG)
    }


    fun startSplashTimeout() {
        stage = Stage.Splash

        Thread(Runnable {
            try {

                Thread.sleep(1500)

                Log.d("SplashViewModel", "SplashViewModel: Dispatching splash timeout")
                listener?.onSplashFinished()
                stage = Stage.Done

            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }).start()

    }


    interface Listener {
        fun onSplashFinished()
    }


    inner class SplashEvent

}
