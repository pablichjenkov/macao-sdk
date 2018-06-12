package com.intervalintl.workflow.view

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import com.intervalintl.workflow.Flow
import com.intervalintl.workflow.view.delegate.FlowViewGroupBinder
import com.intervalintl.workflow.view.delegate.FlowViewGroupSavedState


abstract class FlowViewGroup<F : Flow<*, *>> : ViewGroup {

    private var flowViewGroupBinder: FlowViewGroupBinder<F>? = null


    private val viewGroupBinderCB = object : FlowViewGroupBinder.Callback<F> {
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
        flowViewGroupBinder = FlowViewGroupBinder(this, viewGroupBinderCB)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        flowViewGroupBinder?.onAttachedToWindow()
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is FlowViewGroupSavedState) {
            flowViewGroupBinder!!.onRestoreInstanceState(state)
            super.onRestoreInstanceState(state.superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superParcelable = super.onSaveInstanceState()
        val viewState = FlowViewGroupSavedState(superParcelable)
        flowViewGroupBinder!!.onSaveInstanceState(viewState)

        return viewState
    }

}
