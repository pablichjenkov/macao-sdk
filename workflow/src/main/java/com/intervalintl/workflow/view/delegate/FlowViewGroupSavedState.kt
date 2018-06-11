package com.intervalintl.workflow.view.delegate

import android.os.Parcel
import android.os.Parcelable
import android.view.View


class FlowViewGroupSavedState : View.BaseSavedState {

    lateinit var flowId: String


    constructor(superState: Parcelable) : super(superState) {}

    private constructor(input: Parcel) : super(input) {
        flowId = input.readString()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeString(flowId)
    }

    companion object {

        val CREATOR: Parcelable.Creator<FlowViewGroupSavedState> = object : Parcelable.Creator<FlowViewGroupSavedState> {

            override fun createFromParcel(input: Parcel): FlowViewGroupSavedState {
                return FlowViewGroupSavedState(input)
            }

            override fun newArray(size: Int): Array<FlowViewGroupSavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

}