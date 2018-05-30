package com.intervalintl.workflow

import android.content.Intent
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity


abstract class WorkFlowActivity : AppCompatActivity() {

    protected var flowManager: FlowManager? = null


    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flowManager = FlowManager(this@WorkFlowActivity)

        val rootFlow: Flow<*,*>? = flowManager?.getRootFlow()
        if (rootFlow == null) {
            flowManager?.setRootFlow(onProvideRootFlow())
        }

        flowManager?.getRootFlow<Flow<*, *>>()?.create()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        flowManager?.getRootFlow<Flow<*, *>>()?.resume()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        flowManager?.getRootFlow<Flow<*, *>>()?.pause()
    }

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        flowManager?.getRootFlow<Flow<*, *>>()?.onActivityResult(requestCode, resultCode, data)
    }

    @CallSuper
    override fun onDestroy() {
        flowManager?.getRootFlow<Flow<*, *>>()?.destroy()
        super.onDestroy()
    }

    fun <F : Flow<*, *>> findTraversalSubFlowById(flowId: String): F? {
        return flowManager?.getRootFlow<Flow<*, *>>()?.findTraversalSubFlowById(flowId)
    }

    abstract fun onProvideRootFlow(): Flow<*, *>

}
