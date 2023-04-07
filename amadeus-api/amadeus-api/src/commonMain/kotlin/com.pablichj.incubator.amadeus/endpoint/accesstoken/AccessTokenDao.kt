package com.pablichj.incubator.amadeus.endpoint.accesstoken

import com.pablichj.incubator.amadeus.Database
import com.pablichj.incubator.amadeus.common.ITimeProvider
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.toModel

class AccessTokenDaoDelight(
    private val database: Database,
    private val timeProvider: ITimeProvider
) : IAccessTokenDao {

    override fun insert(accessToken: AccessToken) {
        database.accessTokenTbQueries.insert(
            accessToken.toTable(timeProvider.now().inWholeSeconds)
        )
    }

    override fun lastOrNull(): AccessToken? {
        return database.accessTokenTbQueries.selectLast().executeAsOneOrNull()?.toModel()
    }

    override fun all(): List<AccessToken> {
        return database.accessTokenTbQueries.selectAll().executeAsList().map { it.toModel() }
    }

}

interface IAccessTokenDao {
    fun insert(accessToken: AccessToken)
    fun lastOrNull(): AccessToken?
    fun all(): List<AccessToken>
}