package com.pablichj.incubator.uistate3
import kotlin.js.Date

actual fun timestampMs(): Long {
    return Date.now().toLong()
}
