package com.ncl.common

import android.util.Log
import java.util.ArrayList


class StateContext {

    private val stateServiceList: ArrayList<StateService> = ArrayList()

    /**
     * In case a StateService with the same id is found it will be updated with the coming one.
     * */
    fun registerStateService(stateService: StateService): Boolean {

        var serviceToRemove: StateService? = null

        for (existingState in stateServiceList) {
            if (existingState.getId().equals(stateService.getId())) {
                serviceToRemove = existingState
                break
            }
        }

        // In case the same id was already register then remove the old instance
        stateServiceList.remove(serviceToRemove)

        return stateServiceList.add(stateService)
    }

    fun <T : StateService> getStateService(serviceClass: Class<T>, serviceId: String): T? {

        stateServiceList.forEach {
            if (serviceClass.isAssignableFrom(it.javaClass) && it.getId().equals(serviceId)) {
                return it as T
            }
        }

        Log.e("StateContext","StateService with serviceClass: ${serviceClass.simpleName} " +
                "and serviceId: $serviceId was not found in the StateContext. Make sure you " +
                "register a the proper StateService into the StateContext either when the " +
                "Activity calls the Activity.onProvideStateContext() method or in the " +
                "Coordinator.onStateContextUpdate() method if your Coordinator provides dependencies to its " +
                "descendant children")

        return null
    }

}

interface StateService {
    fun getId(): String
}