package com.intervalintl.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intervalintl.workflow.view.CoordinatorFragment
import io.reactivex.disposables.Disposable


class LoginFragment: CoordinatorFragment<LoginCoordinator>() {

    private lateinit var rootView: View
    private lateinit var loginFlow: LoginCoordinator
    private lateinit var disposable: Disposable


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_login, container, false) as ViewGroup
        return rootView
    }

    override fun onFlowBound(flow: LoginCoordinator) {
        loginFlow = flow
    }

    /*override fun onProvideFlowObserver(): Observer<LoginFlowEvent> {
        return object: Observer<LoginFlowEvent> {

            override fun onNext(loginEvent: LoginFlowEvent) {

                *//*when (loginEvent) {
                    is LoginFlowEvent.LoginIdle {}
                }*//*

            }

            override fun onComplete() {
            }

            override fun onSubscribe(disposable: Disposable) {
                this.disposable = d
            }

            override fun onError(e: Throwable) {
            }
        }
    }*/



}