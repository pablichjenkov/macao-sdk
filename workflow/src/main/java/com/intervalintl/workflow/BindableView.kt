package com.intervalintl.workflow


interface BindableView {
    val coordinatorId: String
    val viewModelId: String
    val viewModelClass: Class<out BaseViewModel>
}
