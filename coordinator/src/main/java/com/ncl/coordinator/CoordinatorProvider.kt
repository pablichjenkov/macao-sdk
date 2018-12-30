package com.ncl.coordinator

interface CoordinatorProvider {
    fun <C: Coordinator> getCoordinatorById(coordinatorId: String): C?
}
