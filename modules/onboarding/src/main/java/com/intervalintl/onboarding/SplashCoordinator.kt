package com.intervalintl.onboarding

import android.util.Log
import com.intervalintl.common.StateContext
import com.intervalintl.workflow.Coordinator
import com.intervalintl.common.Constants
import com.intervalintl.common.domain.screen.ScreenCoordinatorService
import com.intervalintl.workflow.view.ScreenCoordinator


class SplashCoordinator(viewModelId: String) : Coordinator<StateContext>(viewModelId) {

    enum class Stage {
        Idle,
        Splash,
        Done
    }

    var stage = Stage.Idle
        private set

    private var screenCoordinator: ScreenCoordinator? = null

    // TODO: Use PublishSubject from RxJava
    private var listener: Listener? = null


    override fun onStateContextUpdate(stateContext: StateContext) {

        screenCoordinator = stateContext
                .getStateService(ScreenCoordinatorService::class.java, Constants.DEFAULT_SCREEN_SERVICE_ID)
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

        screenCoordinator?.setView(splashFragment, Constants.SPLASH_FRAGMENT_TAG)
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
