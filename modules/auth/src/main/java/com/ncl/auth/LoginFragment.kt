package com.ncl.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.ncl.common.domain.auth.LoginFormData
import com.ncl.coordinator.view.CoordinatorFragment
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class LoginFragment: CoordinatorFragment<AuthCoordinator>() {

    private lateinit var rootView: View
    private lateinit var loginButton: Button
    private lateinit var loginProgressBar: ProgressBar

    private lateinit var authCoordinator: AuthCoordinator
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_login, container, false) as ViewGroup

        loginButton = rootView.findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val loginForm = LoginFormData("fake_email@gmail.com", "fake_password")
            authCoordinator.loginButtonClick(loginForm)
        }

        loginProgressBar = rootView.findViewById(R.id.loginProgressBar)

        return rootView
    }

    override fun onCoordinatorBound(coordinator: AuthCoordinator) {
        this.authCoordinator = coordinator

        authCoordinator.getLoginViewEventPipe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newObserver())
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    private fun newObserver(): Observer<LoginViewEvent> = object: Observer<LoginViewEvent> {

        override fun onNext(event: LoginViewEvent) {

            when (event) {

                is LoginViewEvent.LoginIdle -> {

                    loginProgressBar.visibility = View.GONE
                }

                is LoginViewEvent.ProcessingInternalLogin -> {

                    loginProgressBar.visibility = View.VISIBLE
                }

                is LoginViewEvent.InternalLoginSuccess -> {
                    loginProgressBar.visibility = View.GONE

                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                }

                is LoginViewEvent.InternalLoginFail -> {
                    loginProgressBar.visibility = View.GONE

                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                }

            }

        }

        override fun onSubscribe(d: Disposable) {
            compositeDisposable.add(d)
        }

        override fun onError(e: Throwable) {}

        override fun onComplete() {}

    }

}