package com.pablichj.incubator.amadeus.endpoint.accesstoken

import com.pablichj.incubator.amadeus.Database
import com.pablichj.incubator.amadeus.model.AccessToken
import com.pablichj.incubator.amadeus.model.toModel

class AccessTokenDaoDelight(
    private val database: Database
) : IAccessTokenDao {

    override fun insert(accessToken: AccessToken) {
        database.accessTokenTbQueries.insert(
            accessToken.toDb()
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