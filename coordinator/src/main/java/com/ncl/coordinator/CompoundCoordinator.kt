package com.ncl.coordinator

import android.content.Intent
import androidx.annotation.CallSuper
import java.util.ArrayList


/**
 * Don't keep strong references to Views, Activities or Fragments in this class, this class persist
 * Configuration Changes so retaining references will cause memory leaks.
 */
abstract class CompoundCoordinator: Coordinator {

    private var children: MutableList<Coordinator>? = null


    constructor(id: String) : super(id)

    constructor(id: String, children: MutableList<Coordinator>) : super(id) {
        this.children = children
    }


    // region: CompoundCoordinator Tree Methods

    fun launch(coordinator: Coordinator) {

        children?.let {

            if (!it.contains(coordinator)) {
                it.add(coordinator)
            }

        }
        ?: run {
            children = ArrayList()
            children?.add(coordinator)
        }

        coordinator.start()

    }

    fun removeChild(coordinatorId: String) {

        children?.iterator()?.let { childrenIterator ->

            var childToRemove : Coordinator? = null

            while (childrenIterator.hasNext()) {

                val child = childrenIterator.next()

                if (child.coordinatorId.equals(coordinatorId)) {
                    childToRemove = child
                    break
                }
            }

            childToRemove?.let { it ->
                it.stop()
                children?.remove(it)
            }

        }

    }

    override fun <F : Coordinator> depthFirstSearchById(coordinatorId: String): F? {

        if (this.coordinatorId.equals(coordinatorId)) {
            return this as F
        }

        return children?.let {
            var result: Coordinator? = null

            for (childFlow in it) {
                result = childFlow.depthFirstSearchById(coordinatorId)

                if (result != null) {
                    return result as F
                }
            }

            return null

        }

    }

    /**
     * Only look up in the children coordinator. For a whole tree search use depthFirstSearchById
     * method.
     * */
    fun <F : Coordinator> getChildById(id: String): F? {

        return children?.let {
            var result: F? = null

            for (child in it) {
                if (child.coordinatorId.equals(id)) {
                    result = child as F
                    break
                }
            }

            return result

        }

    }

    // endregion


    // region: Activity Lifecycle Events

    @CallSuper
    override fun onCreate() {
        children?.let {
            for (childFlow in it) {
                childFlow.onCreate()
            }
        }
    }

    @CallSuper
    override fun onResume() {
        children?.let {
            for (childFlow in it) {
                childFlow.onResume()
            }
        }
    }

    @CallSuper
    override fun onPause() {
        children?.let {
            for (childFlow in it) {
                childFlow.onPause()
            }
        }
    }

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) : Boolean {

        return children?.iterator()?.let {
            var consumed = false
            while (it.hasNext() && !consumed) {
                consumed = it.next().onActivityResult(requestCode, resultCode, data)
            }
            return consumed

        } ?: false

    }

    @CallSuper
    override fun onDestroy() {
        children?.let {
            for (childFlow in it) {
                childFlow.onDestroy()
            }
        }
    }

    @CallSuper
    override fun onBackPressed() : Boolean {

        return children?.iterator()?.let {
            var consumed = false
            while (it.hasNext() && !consumed) {
                consumed = it.next().onBackPressed()
            }
            return consumed

        } ?: false

    }

    // endregion

}