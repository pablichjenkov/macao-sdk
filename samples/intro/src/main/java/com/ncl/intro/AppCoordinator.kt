package com.ncl.intro

import com.ncl.auth.AuthCoordinator
import com.ncl.auth.ModelEvent
import com.ncl.common.Constants
import com.ncl.common.domain.auth.AuthApi
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.coordinator.CompoundCoordinator
import com.ncl.onboarding.OnboardingCoordinator
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class AppCoordinator(id: String,
                     val screenCoordinator: ScreenCoordinator,
                     val authApi: AuthApi

) : CompoundCoordinator(id) {


    enum class Stage {
        Idle,
        Boarding,
        Authorizing
    }

    private var stage: Stage = Stage.Idle
    private lateinit var onboardindCoordinator : OnboardingCoordinator
    private lateinit var authCoordinator: AuthCoordinator
    private val compositeDisposable = CompositeDisposable()


    override fun start() {
        if (stage == Stage.Idle) {
            launch(screenCoordinator)

            stage = Stage.Boarding
            launchOnboarding()
        }
    }

    override fun stop() {
        compositeDisposable.clear()
    }

    override fun onBackPressed(): Boolean {
        val consumed = super.onBackPressed()

        if (!consumed) {
            stage = Stage.Idle
        }

        return consumed
    }

    fun launchOnboarding() {

        onboardindCoordinator = OnboardingCoordinator(Constants.ONBOARDING_COORDINATOR_ID,
                screenCoordinator)

        onboardindCoordinator.getEventPipe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onboardingObserver)

        launch(onboardindCoordinator)
    }

    fun launchLogin() {

        authCoordinator = AuthCoordinator(Constants.AUTH_COORDINATOR_ID,
                screenCoordinator,
                authApi)

        authCoordinator.getModelEventPipe().subscribe(loginObserver)

        launch(authCoordinator)
    }

    fun removeLogin() {
        removeChild(Constants.AUTH_COORDINATOR_ID)
    }

    private val onboardingObserver = object : Observer<OnboardingCoordinator.Event> {

        override fun onSubscribe(d: Disposable) {
            compositeDisposable.add(d)
        }

        override fun onNext(event: OnboardingCoordinator.Event) {

            when (event) {

                is OnboardingCoordinator.Event.Done -> {
                    stage = Stage.Authorizing
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

                is ModelEvent.Cancel -> {
                    stage = Stage.Boarding
                    removeLogin()
                }
                is ModelEvent.Complete -> {}
            }
        }

        override fun onError(e: Throwable) {}

        override fun onComplete() {}

    }

}