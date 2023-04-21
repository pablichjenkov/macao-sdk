package com.pablichj.incubator.amadeus.common

// TODO: Provide this from BuildConfig
enum class Envs(val hostUrl: String) {
    TEST("https://test.api.amadeus.com/"),
    PRODUCTION("https://api.amadeus.com/")
}