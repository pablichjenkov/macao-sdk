package com.intervalintl.common.domain.user

import com.intervalintl.common.Constants
import com.intervalintl.common.StateService


class UserService : StateService {

    override fun getId(): String {
        return Constants.DEFAULT_USER_MANAGER_SERVICE_ID
    }

    fun getUserManager(): UserManager {
        return UserManagerMock()
    }

}