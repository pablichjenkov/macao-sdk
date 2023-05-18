package com.pablichj.incubator.uistate3.example.hotelBooking

import com.pablichj.incubator.amadeus.endpoint.accesstoken.IAccessTokenDao
import com.pablichj.incubator.amadeus.endpoint.accesstoken.model.AccessToken

class InMemoryAccessTokenDao : IAccessTokenDao {

    companion object {
        private val accessTokens = mutableListOf <AccessToken>()
    }

    override fun all(): List<AccessToken> {
        return accessTokens
    }

    override fun insert(accessToken: AccessToken) {
        accessTokens.add(accessToken)
    }

    override fun lastOrNull(): AccessToken? {
        return accessTokens.lastOrNull()
    }
}