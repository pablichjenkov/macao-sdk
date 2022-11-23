package com.ncl.intro

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.ncl.common.Constants
import com.ncl.common.domain.auth.AuthApiMock
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.common.domain.screen.ScreenCoordinatorImpl
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.CoordinatorProvider
import com.ncl.coordinator.RotationPersister


class IntroActivity : AppCompatActivity(), CoordinatorProvider {

    private lateinit var rotationPersister: RotationPersister
    private lateinit var appCoordinator : AppCoordinator
    private var authManager = AuthApiMock()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        setContentView(R.layout.activity_intro)

        rotationPersister = RotationPersister(this@IntroActivity)
        val viewContainer: ViewGroup = findViewById(R.id.introActivityViewContainer)

        rotationPersister.getRootCoordinator<AppCoordinator>()?.let {
            appCoordinator = it

            // Set the new created fragmentManager and viewContainer
            val screenCoordinator = it.getChildById<ScreenCoordinator>(Constants.SCREEN_COORDINATOR_ID)
            screenCoordinator?.onConfigurationChange(supportFragmentManager, viewContainer)


        } ?: run {

            val screenCoordinator = ScreenCoordinatorImpl(Constants.SCREEN_COORDINATOR_ID,
                    supportFragmentManager,
                    viewContainer)

            val appCoordinatorCopy = AppCoordinator(Constants.APP_COORDINATOR_ID,
                    screenCoordinator,
                    authManager)

            appCoordinator = appCoordinatorCopy

            rotationPersister.setRootCoordinator(appCoordinatorCopy)

            appCoordinatorCopy.start()

        }

    }


    // Bellow region is useful if we want to handle Activity Lifecycle in our coordinators
    // region: Activity Lifecycle

    override fun onResume() {
        super.onResume()
        appCoordinator.onResume()
    }

    override fun onPause() {
        super.onPause()
        appCoordinator.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        appCoordinator.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        appCoordinator.onDestroy()
    }

    override fun onBackPressed() {
        val backConsumed = appCoordinator.onBackPressed()
        if (backConsumed == false) {
            super.onBackPressed()
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val backPressedConsumed = appCoordinator.onBackPressed()
                if (!backPressedConsumed) {
                    finish()
                }
            }
        }

    // endregion


    override fun <C : Coordinator> getCoordinatorById(coordinatorId: String): C? {
        return appCoordinator.depthFirstSearchById(coordinatorId)
    }

}
