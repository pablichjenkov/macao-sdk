package com.ncl.onboarding

import android.util.Log
import com.ncl.common.Constants
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.coordinator.Coordinator
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


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

    private var eventPipe: PublishSubject<Event> = PublishSubject.create()


    override fun start() {

        if (stage == Stage.Idle) {
            showSplashScreen()
        } else if (stage == Stage.Done) {
            Log.d("SplashViewModel", "Calling start but SplashViewModel is done.")
        }
    }

    override fun stop() {

    }

    fun getEventPipe(): Observable<Event> {
        return eventPipe
    }

    private fun showSplashScreen() {
        val splashFragment = SplashFragment()
        splashFragment.setCoordinatorId(coordinatorId)
        screenCoordinator.setView(splashFragment, Constants.SPLASH_FRAGMENT_TAG)
    }


    fun startSplashTimeout() {
        stage = Stage.Splash

        Thread {
            try {

                Thread.sleep(2000)

                Log.d("SplashViewModel", "SplashViewModel: Dispatching splash timeout")
                stage = Stage.Done
                eventPipe.onNext(Event.Done())

            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()

    }

    sealed class Event {
        class Done : Event()
    }

}
