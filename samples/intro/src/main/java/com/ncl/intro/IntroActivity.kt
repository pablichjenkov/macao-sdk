package com.ncl.intro

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ncl.common.Constants
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.common.domain.screen.ScreenCoordinatorImpl


class IntroActivity : AppCompatActivity() {

    lateinit var appCoordinator : AppCoordinator
    lateinit var screenCoordinator: ScreenCoordinator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val viewContainer: ViewGroup = findViewById(R.id.introActivityViewContainer)

        screenCoordinator = ScreenCoordinatorImpl(supportFragmentManager, viewContainer)

        appCoordinator = AppCoordinator(Constants.APP_COORDINATOR_ID, screenCoordinator)

        appCoordinator.start()

    }

}
