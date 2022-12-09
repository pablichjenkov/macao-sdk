import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

@OptIn(ExperimentalComposeLibrary::class)
kotlin {
    jvm("desktop")

    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material3)
            }
        }
        /*named("desktopMain") {
            dependencies {
                implementation("uk.co.caprica:vlcj:4.7.0")
            }
        }*/
    }
}
