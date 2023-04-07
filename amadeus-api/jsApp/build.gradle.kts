plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting  {
            dependencies {
                implementation("com.pablichj:component:0.1.5")
                implementation(project(":shared"))
                implementation(project(":amadeus-api"))
                implementation(compose.web.core)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.4")
            }
        }
    }
}

compose.experimental {
    web.application {}
}

