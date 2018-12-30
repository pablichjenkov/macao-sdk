package com.ncl.onboarding

import com.ncl.common.Constants
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.coordinator.CompoundCoordinator
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


class OnboardingCoordinator(coordinatorId: String,
                            private val screenCoordinator: ScreenCoordinator

) : CompoundCoordinator(coordinatorId) {

    private var eventPipe: PublishSubject<Event> = PublishSubject.create<Event>()
    private val compositeDisposable = CompositeDisposable()


    override fun start() {

        val splashCoordinator = SplashCoordinator(Constants.SPLASH_COORDINATOR_ID,
                screenCoordinator)

        splashCoordinator.getEventPipe()
                .subscribe(splashCoordinatorObserver)

        launch(splashCoordinator)
    }

    override fun stop() {

    }

    fun getEventPipe(): Observable<Event> {
        return eventPipe
    }


    sealed class Event {

        class Done : Event() {}

    }


    private val splashCoordinatorObserver = object : Observer<SplashCoordinator.Event> {

        override fun onComplete() {}

        override fun onSubscribe(d: Disposable) {
            compositeDisposable.add(d)
        }

        override fun onNext(event: SplashCoordinator.Event) {

            when (event) {

                is SplashCoordinator.Event.Done -> {
                    eventPipe.onNext(Event.Done())
                }

            }

        }

        override fun onError(e: Throwable) {

        }

    }

}
