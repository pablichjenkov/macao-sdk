package com.ncl.coordinator.view

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.ViewGroup
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.view.delegate.CoordinatorViewGroupBinder
import com.ncl.coordinator.view.delegate.CoordinatorViewGroupSavedState


abstract class CoordinatorViewGroup<C : Coordinator> : ViewGroup, CoordinatorBindableView {

    private var coordinatorViewGroupBinder: CoordinatorViewGroupBinder<C>? = null


    private val viewGroupBinderCB = object : CoordinatorViewGroupBinder.Callback<C> {
        override fun onCoordinatorBound(coordinator: C) {
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
        coordinatorViewGroupBinder?.onSaveInstanceState(viewState)

        return viewState
    }

}
