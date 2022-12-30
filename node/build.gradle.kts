plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm("desktop")

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material)
            }
        }
        /*named("desktopMain") {
            dependencies {
                implementation("uk.co.caprica:vlcj:4.7.0")
            }
        }*/
    }
}
