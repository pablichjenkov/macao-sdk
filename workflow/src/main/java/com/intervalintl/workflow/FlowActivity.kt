package com.intervalintl.workflow

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


abstract class FlowActivity<LiftedState> : AppCompatActivity() {

    private var flowRotationPersister: FlowRotationPersister? = null
    private var callCoordinatorStartWhenOnResume = false
    private var rootFlow: Flow<LiftedState, *>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flowRotationPersister = FlowRotationPersister(this@FlowActivity)

        rootFlow = flowRotationPersister?.getRootFlow()

        if (rootFlow == null) {
            callCoordinatorStartWhenOnResume = true
            rootFlow = onCreateRootFlow()

            rootFlow?.let{
                flowRotationPersister?.setRootFlow(it)
            }

        }

        rootFlow?.onCreate()
    }

    override fun onResume() {
        super.onResume()
        rootFlow?.onResume()
    }

    override fun onPostResume() {
        super.onPostResume()
        rootFlow?.dispatchStateContextUpdate(onProvideStateContext())

        // The start() method on the rootFlow will only be called the first time this Activity
        // is created, not every time it gets re-created due to configuration changes.
        if (callCoordinatorStartWhenOnResume) {
            rootFlow?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        rootFlow?.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        rootFlow?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        rootFlow?.onDestroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        val backConsumed = rootFlow?.onBackPressed()
        if (backConsumed == false) {
            super.onBackPressed()
        }
    }

    fun <F : Flow<LiftedState, *>> findFlowById(flowId: String): F? {
        return rootFlow?.depthFirstSearchFlowById(flowId)
    }

    abstract fun onCreateRootFlow(): Flow<LiftedState, *>

    abstract fun onProvideStateContext(): LiftedState

}
