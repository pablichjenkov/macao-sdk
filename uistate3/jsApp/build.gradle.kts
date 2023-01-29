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
                implementation(project(":uistate3"))
                implementation(compose.web.core)
            }
        }
    }
}

compose.experimental {
    web.application {}
}

