package com.ncl.coordinator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class CoordinatorActivity : AppCompatActivity(), CoordinatorProvider {

    private var rotationPersister: RotationPersister? = null
    private var callCoordinatorStartWhenOnResume = false
    private var rootCoordinator: Coordinator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rotationPersister = RotationPersister(this@CoordinatorActivity)

        rootCoordinator = rotationPersister?.getRootCoordinator()

        if (rootCoordinator == null) {
            callCoordinatorStartWhenOnResume = true
            rootCoordinator = provideRootCoordinator()

            rootCoordinator?.let{
                rotationPersister?.setRootCoordinator(it)
            }

        }

        rootCoordinator?.onCreate()
    }

    override fun onResume() {
        super.onResume()
        rootCoordinator?.onResume()
    }

    override fun onPostResume() {
        super.onPostResume()

        // The start() method on the rootCoordinator will only be called the first time this Activity
        // is created, not every time it gets re-created due to configuration changes.
        if (callCoordinatorStartWhenOnResume) {
            rootCoordinator?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        rootCoordinator?.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        rootCoordinator?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        rootCoordinator?.onDestroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        val backConsumed = rootCoordinator?.onBackPressed()
        if (backConsumed == false) {
            super.onBackPressed()
        }
    }

    override fun <F : Coordinator> getCoordinatorById(coordinatorId: String): F? {
        return rootCoordinator?.depthFirstSearchById(coordinatorId)
    }

    abstract fun provideRootCoordinator(): Coordinator

}
