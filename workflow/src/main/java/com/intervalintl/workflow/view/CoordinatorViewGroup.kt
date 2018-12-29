package com.intervalintl.workflow.view

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import com.intervalintl.workflow.Coordinator
import com.intervalintl.workflow.view.delegate.CoordinatorViewGroupBinder
import com.intervalintl.workflow.view.delegate.CoordinatorViewGroupSavedState


abstract class CoordinatorViewGroup<F : Coordinator<*>> : ViewGroup {

    private var coordinatorViewGroupBinder: CoordinatorViewGroupBinder<F>? = null


    private val viewGroupBinderCB = object : CoordinatorViewGroupBinder.Callback<F> {
        override fun onFlowBound(flow: F) {
            // TODO(Pablo): Can define an abstract method and call it from here.
        }
    }


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        coordinatorViewGroupBinder = CoordinatorViewGroupBinder(this, viewGroupBinderCB)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        coordinatorViewGroupBinder?.onAttachedToWindow()
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is CoordinatorViewGroupSavedState) {
            coordinatorViewGroupBinder!!.onRestoreInstanceState(state)
            super.onRestoreInstanceState(state.superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superParcelable = super.onSaveInstanceState()
        val viewState = CoordinatorViewGroupSavedState(superParcelable)
        coordinatorViewGroupBinder!!.onSaveInstanceState(viewState)

        return viewState
    }

}
