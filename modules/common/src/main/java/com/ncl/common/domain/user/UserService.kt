package com.ncl.common.domain.user

import com.ncl.common.Constants
import com.ncl.common.StateService


class UserService : StateService {

    override fun getId(): String {
        return Constants.DEFAULT_USER_MANAGER_SERVICE_ID
    }

    fun getUserManager(): UserManager {
        return UserManagerMock()
    }

}