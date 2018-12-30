package com.ncl.onboarding

import com.ncl.common.Constants
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.coordinator.CompoundCoordinator
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class OnboardingCoordinator(coordinatorId: String,
                            private val screenCoordinator: ScreenCoordinator

) : CompoundCoordinator(coordinatorId) {

    private var publishSubject: PublishSubject<Event> = PublishSubject.create<Event>()


    override fun start() {

        val splashCoordinator = SplashCoordinator(Constants.SPLASH_COORDINATOR_ID,
                screenCoordinator)

        splashCoordinator.setListener(object : SplashCoordinator.Listener {
            override fun onSplashFinished() {
                publishSubject.onNext(Event.Done())
            }
        })

        splashCoordinator.start()
    }

    override fun stop() {

    }

    fun getEventPipe(): Observable<Event> {
        return publishSubject
    }


    sealed class Event {

        class Done : Event() {}

    }

}
