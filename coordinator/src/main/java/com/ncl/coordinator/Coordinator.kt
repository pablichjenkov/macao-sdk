package com.ncl.coordinator

import android.content.Intent


/**
 * Don't keep strong references to Views, Activities or Fragments in this class, this class persist
 * Configuration Changes so retaining references will cause memory leaks.
 */
abstract class Coordinator(val coordinatorId: String) {


    // region: Coordinator Tree Events

    open fun <F : Coordinator> depthFirstSearchById(coordinatorId: String): F? {

        if (this.coordinatorId.equals(coordinatorId)) {
            return this as F
        }

        return null
    }

    // endregion


    // region: abstract Coordinator API, to be override

    abstract fun start()

    abstract fun stop()

    // endregion


    // region: Activity Lifecycle Events

    open fun onCreate() {}

    open fun onResume() {}

    open fun onPause() {}

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) : Boolean {
        return false
    }

    open fun onDestroy() {}

    open fun onBackPressed() : Boolean {
        return false
    }

    // endregion

}
