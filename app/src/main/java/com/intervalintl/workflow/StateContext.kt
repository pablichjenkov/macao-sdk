package com.intervalintl.workflow

import java.util.ArrayList


class StateContext {

    private val stateList: ArrayList<State> = ArrayList()


    fun registerState(state: State): Boolean {

        for (existingState in stateList) {
            if (existingState.getId().equals(state.getId())) {
                return false
            }
        }

        return stateList.add(state)
    }

    fun <T : State> getState(serviceClass: Class<T>, serviceId: String): T? {
        var result: T? = null

        for (state in stateList) {
            if (serviceClass.isAssignableFrom(state.javaClass) && state.getId().equals(serviceId)) {
                result = state as T
                break
            }
        }

        return result
    }

}

interface State {
    fun getId(): String
}