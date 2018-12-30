package com.ncl.onboarding

import android.util.Log
import com.ncl.common.Constants
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.coordinator.Coordinator


class SplashCoordinator(coordinatorId: String,
                        private val screenCoordinator: ScreenCoordinator

) : Coordinator(coordinatorId) {

    enum class Stage {
        Idle,
        Splash,
        Done
    }

    var stage = Stage.Idle
        private set

    // TODO: Use PublishSubject from RxJava
    private var listener: Listener? = null


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
        splashFragment.setCoordinatorId(coordinatorId)
        splashFragment.setCoordinator(this)
        screenCoordinator.setView(splashFragment, Constants.SPLASH_FRAGMENT_TAG)
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

    // TODO: Use PublishSubject Events
    interface Listener {
        fun onSplashFinished()
    }


    inner class SplashEvent

}
