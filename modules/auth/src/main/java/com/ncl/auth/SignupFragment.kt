package com.ncl.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.ncl.common.domain.auth.SignUpFormData
import com.ncl.coordinator.view.CoordinatorFragment
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class SignupFragment: CoordinatorFragment<AuthCoordinator>() {

    private lateinit var rootView: View
    private lateinit var signupButton: Button
    private lateinit var signupProgressBar: ProgressBar

    private lateinit var authCoordinator: AuthCoordinator
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_signup, container, false) as ViewGroup

        signupButton = rootView.findViewById(R.id.signupButton)

        signupButton.setOnClickListener {

            val signupForm = SignUpFormData("fake_name",
                    "fake_email",
                    "786-337-1234",
                    "fake_password",
                    "33019")

            authCoordinator.signupButtonClick(signupForm)
        }

        signupProgressBar = rootView.findViewById(R.id.signupProgressBar)

        return rootView
    }

    override fun onCoordinatorBound(coordinator: AuthCoordinator) {
        this.authCoordinator = coordinator

        authCoordinator.getSignupViewEventPipe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newObserver())
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    private fun newObserver(): Observer<SignupViewEvent> = object: Observer<SignupViewEvent> {

        override fun onNext(event: SignupViewEvent) {

            when (event) {

                is SignupViewEvent.SignupIdle -> {

                    signupProgressBar.visibility = View.GONE
                }

                is SignupViewEvent.ProcessingInternalSignup -> {

                    signupProgressBar.visibility = View.VISIBLE
                }

                is SignupViewEvent.InternalSignupSuccess -> {
                    signupProgressBar.visibility = View.GONE

                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                }

                is SignupViewEvent.InternalSignupFail -> {
                    signupProgressBar.visibility = View.GONE

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