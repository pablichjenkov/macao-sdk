package com.pablichj.incubator.amadeus.endpoint.accesstoken

import com.pablichj.incubator.amadeus.Database
import com.pablichj.incubator.amadeus.model.AccessToken

class AccessTokenManager(
    private val accessTokenDao: IAccessTokenDao
) : IAccessTokenManager {

    override fun insert(accessToken: AccessToken) {
        accessTokenDao.insert(accessToken)
    }

    override fun lastOrNull(): AccessToken? {
        return accessTokenDao.lastOrNull()
    }

    override fun all(): List<AccessToken> {
        return accessTokenDao.all()
    }

    companion object {
        fun create(
            database: Database
        ): IAccessTokenManager {
            return AccessTokenManager(
                AccessTokenDaoDelight(
                    database
                )
            )
        }
    }
}

interface IAccessTokenManager {
    fun insert(accessToken: AccessToken)
    fun lastOrNull(): AccessToken?
    fun all(): List<AccessToken>
}