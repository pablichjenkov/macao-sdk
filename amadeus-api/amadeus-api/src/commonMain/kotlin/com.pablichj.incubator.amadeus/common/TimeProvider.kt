package com.pablichj.incubator.amadeus.common

import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface ITimeProvider {
    fun now(): Duration
}

class DefaultTimeProvider : ITimeProvider {

    override fun now(): Duration {
        return Clock.System.now().epochSeconds.seconds
    }

}