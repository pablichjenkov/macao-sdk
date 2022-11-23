package com.ncl.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ncl.coordinator.view.CoordinatorFragment


class AuthFragment: CoordinatorFragment<AuthCoordinator>() {

    private lateinit var rootView: View
    private lateinit var loginOptButton: Button
    private lateinit var signupOptButton: Button
    private lateinit var oauth2OptButton: Button
    private lateinit var reservationOptButton: Button
    private lateinit var authCoordinator: AuthCoordinator


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
                              , savedInstanceState: Bundle?): View {

        rootView = inflater.inflate(R.layout.fragment_auth, container, false) as ViewGroup

        loginOptButton = rootView.findViewById(R.id.loginOptButton)

        loginOptButton.setOnClickListener {
            authCoordinator.startLoginFlow()
        }

        signupOptButton = rootView.findViewById(R.id.signupOptButton)

        signupOptButton.setOnClickListener {
            authCoordinator.startSignupFlow()
        }

        oauth2OptButton = rootView.findViewById(R.id.oauth2OptButton)

        oauth2OptButton.setOnClickListener {
            authCoordinator.startOAuth2Flow()
        }

        reservationOptButton = rootView.findViewById(R.id.reservationOptButton)

        reservationOptButton.setOnClickListener {
            authCoordinator.startReservationLoginFlow()
        }

        return rootView
    }

    override fun onCoordinatorBound(coordinator: AuthCoordinator) {
        this.authCoordinator = coordinator

        authCoordinator.getAuthOptionsEventPipe(this)

    }

}