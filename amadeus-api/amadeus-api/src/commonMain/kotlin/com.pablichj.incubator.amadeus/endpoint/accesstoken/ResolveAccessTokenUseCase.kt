package com.pablichj.incubator.amadeus.endpoint.accesstoken

import com.pablichj.incubator.amadeus.common.SingleUseCaseNoParam
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResolveAccessTokenUseCase(
    private val dispatcher: Dispatchers,
    private val accessTokenDao: IAccessTokenDao
) : SingleUseCaseNoParam<AccessToken?> {

    override suspend fun doWork(): AccessToken? {
        return withContext(dispatcher.Unconfined) {
            //accessTokenDao.lastOrNull()
            accessTokenDao.all().lastOrNull()
        }
    }

    companion object {
        private const val tokenUrl = "https://test.api.amadeus.com/v1/security/oauth2/token"
        const val AccessTokenGrantType = "client_credentials"
    }

}