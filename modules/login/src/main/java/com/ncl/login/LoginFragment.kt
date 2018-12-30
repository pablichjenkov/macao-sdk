package com.ncl.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.ncl.coordinator.view.CoordinatorFragment
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class LoginFragment: CoordinatorFragment<LoginCoordinator>() {

    private lateinit var rootView: View
    private lateinit var loginButton: Button
    private lateinit var loginProgressBar: ProgressBar

    private lateinit var loginCoordinator: LoginCoordinator
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_login, container, false) as ViewGroup

        loginButton = rootView.findViewById(R.id.loginButton)
        loginButton.setOnClickListener { loginCoordinator.loginButtonClick() }

        loginProgressBar = rootView.findViewById(R.id.loginProgressBar)

        return rootView
    }

    override fun onCoordinatorBound(coordinator: LoginCoordinator) {
        this.loginCoordinator = coordinator

        loginCoordinator.getViewEventPipe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newObserver())
    }

    private fun newObserver(): Observer<ViewEvent> = object: Observer<ViewEvent> {

        override fun onNext(event: ViewEvent) {

            when (event) {

                is ViewEvent.LoginIdle -> {

                    loginProgressBar.visibility = View.GONE
                }

                is ViewEvent.ProcessingInternalLogin -> {

                    loginProgressBar.visibility = View.VISIBLE
                }

                is ViewEvent.InternalLoginSuccess -> {
                    loginProgressBar.visibility = View.GONE

                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
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