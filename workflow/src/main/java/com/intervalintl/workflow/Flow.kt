package com.intervalintl.workflow

import android.content.Intent
import android.support.annotation.CallSuper
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject


/**
 * Don't keep references of Views, Activities or Fragments in this class, this class persist
 * Configuration Changes.
 */
abstract class Flow<Input: Any, Output: Flow.Event> {

    val flowId: String
    var children: MutableList<Flow<*, *>>?
            get() = children
            set(children) { this.children = children }

    protected lateinit var input: Input
    protected val subject: Subject<out Output> by lazy { createBehaviorSubject() }

    constructor(flowId: String) {
        this.flowId = flowId
    }

    constructor(flowId: String, children: MutableList<Flow<*, *>>) {
        this.flowId = flowId
        this.children = children
    }


    // region: Flow Graph Events

    protected fun attachChildFlow(childFlow: Flow<*, *>) {
        if (children == null) {
            children = ArrayList()
        }
        children?.add(childFlow)
    }

    fun <F : Flow<*, *>> getChildFlowById(flowId: String): F? {

        return children?.let {
            var result: F? = null

            for (child in it) {
                if (child.flowId.equals(flowId)) {
                    result = child as F
                    break
                }
            }

            return result

        }

    }

    fun <F : Flow<*, *>> findTraversalSubFlowById(subFlowId: String): F? {

        if (this.flowId.equals(subFlowId)) {
            return this as F
        }

        return children?.let {
            var result: Flow<*, *>? = null

            for (childFlow in it) {
                result = childFlow.findTraversalSubFlowById(subFlowId)

                if (result != null) {
                    return result as F
                }
            }

            return null

        }

    }

    fun subscribe(): Observable<out Output> {
        return subject
    }

    abstract fun createBehaviorSubject(): BehaviorSubject<out Output>

    abstract fun init(input: Input)

    abstract fun process()

    abstract fun abort()

    // endregion


    // region: Activity Lifecycle Events

    @CallSuper
    fun create() {
        children?.let {
            for (childFlow in it) {
                childFlow.create()
            }
        }
    }

    @CallSuper
    fun resume() {
        children?.let {
            for (childFlow in it) {
                childFlow.resume()
            }
        }
    }

    @CallSuper
    fun pause() {
        children?.let {
            for (childFlow in it) {
                childFlow.pause()
            }
        }
    }

    @CallSuper
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) : Boolean {

        return children?.iterator()?.let {
            var consumed = false
            while (it.hasNext() && !consumed) {
                consumed = it.next().onActivityResult(requestCode, resultCode, data)
            }
            return consumed

        } ?: false

    }

    @CallSuper
    fun destroy() {
        children?.let {
            for (childFlow in it) {
                childFlow.destroy()
            }
        }
    }

    @CallSuper
    fun onBackPressed() : Boolean {

        return children?.iterator()?.let {
            var consumed = false
            while (it.hasNext() && !consumed) {
                consumed = it.next().onBackPressed()
            }
            return consumed

        } ?: false

    }

    // endregion

    interface Event

}
