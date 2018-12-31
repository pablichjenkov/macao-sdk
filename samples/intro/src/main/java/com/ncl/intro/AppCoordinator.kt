package com.ncl.intro

import com.ncl.common.Constants
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.coordinator.CompoundCoordinator
import com.ncl.login.LoginCoordinator
import com.ncl.login.ModelEvent
import com.ncl.onboarding.OnboardingCoordinator
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class AppCoordinator(id: String,
                     val screenCoordinator: ScreenCoordinator

) : CompoundCoordinator(id) {


    enum class Stage {
        Idle,
        Boarding,
        Authorizing
    }

    private var stage: Stage = Stage.Idle
    private lateinit var onboardindCoordinator : OnboardingCoordinator
    private val compositeDisposable = CompositeDisposable()


    override fun start() {
        if (Stage.Idle == stage) {
            launchOnboarding()
        }
    }

    override fun stop() {

    }

    fun launchOnboarding() {
        stage = Stage.Boarding

        onboardindCoordinator = OnboardingCoordinator(Constants.ONBOARDING_COORDINATOR_ID,
                screenCoordinator)

        onboardindCoordinator.getEventPipe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onboardingObserver)

        launch(onboardindCoordinator)
    }

    fun launchLogin() {

        val loginCoordinator = LoginCoordinator(Constants.LOGIN_COORDINATOR_ID,
                screenCoordinator)

        loginCoordinator.getModelEventPipe().subscribe(loginObserver)

        launch(loginCoordinator)
    }

    fun removeLogin() {
        removeChild(Constants.LOGIN_COORDINATOR_ID)
    }

    private val onboardingObserver = object : Observer<OnboardingCoordinator.Event> {

        override fun onSubscribe(d: Disposable) {
            compositeDisposable.add(d)
        }

        override fun onNext(event: OnboardingCoordinator.Event) {

            when (event) {

                is OnboardingCoordinator.Event.Done -> {
                    launchLogin()
                }


            }
        }

        override fun onError(e: Throwable) {}

        override fun onComplete() {}

    }

    private val loginObserver = object : Observer<ModelEvent> {

        override fun onSubscribe(d: Disposable) {
            compositeDisposable.add(d)
        }

        override fun onNext(event: ModelEvent) {

            when (event) {

                is ModelEvent.Done -> {
                    removeLogin()
                }


            }
        }

        override fun onError(e: Throwable) {}

        override fun onComplete() {}

    }

}