package com.ncl.coordinator.view.delegate

import android.os.Parcel
import android.os.Parcelable
import android.view.View


class CoordinatorViewGroupSavedState : View.BaseSavedState {

    lateinit var coordinatorId: String


    constructor(superState: Parcelable) : super(superState) {}

    private constructor(input: Parcel) : super(input) {
        coordinatorId = input.readString()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeString(coordinatorId)
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