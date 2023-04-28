package com.pablichj.incubator.amadeus.common

import kotlinx.datetime.Clock
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

interface ITimeProvider {
    fun epochSeconds(): Duration
}

class DefaultTimeProvider : ITimeProvider {

    override fun epochSeconds(): Duration {
        return Clock.System.now().epochSeconds.toDuration(DurationUnit.SECONDS)
    }

}