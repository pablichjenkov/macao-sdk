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
                implementation(project(":shared-hw"))
                implementation(compose.web.core)
            }
        }
    }
}

compose.experimental {
    web.application {}
}

