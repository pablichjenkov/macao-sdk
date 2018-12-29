package com.intervalintl.workflow.view.delegate

import android.os.Parcel
import android.os.Parcelable
import android.view.View


class CoordinatorViewGroupSavedState : View.BaseSavedState {

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

        val CREATOR: Parcelable.Creator<CoordinatorViewGroupSavedState> = object : Parcelable.Creator<CoordinatorViewGroupSavedState> {

            override fun createFromParcel(input: Parcel): CoordinatorViewGroupSavedState {
                return CoordinatorViewGroupSavedState(input)
            }

            override fun newArray(size: Int): Array<CoordinatorViewGroupSavedState?> {
                return arrayOfNulls(size)
            }
        }
    }

}