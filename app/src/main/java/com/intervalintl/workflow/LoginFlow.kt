package com.intervalintl.workflow

import io.reactivex.subjects.BehaviorSubject


class LoginFlow : Flow<LoginInput, LoginFlowEvent> {

    constructor(flowId: String): super(flowId)
    constructor(flowId: String, children: MutableList<Flow<*, *>>): super(flowId, children)

    override fun createBehaviorSubject(): BehaviorSubject<out LoginFlowEvent>
            = BehaviorSubject.createDefault(LoginFlowEvent.LoginIdle())

    override fun init(input: LoginInput) {

    }

    override fun process() {

    }

    override fun abort() {

    }


}


data class LoginInput(val username: String, val password: String)

sealed class LoginFlowEvent : Flow.Event {

    class LoginIdle : LoginFlowEvent()

}
