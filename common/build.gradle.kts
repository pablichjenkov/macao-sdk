import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
}

group = "com.pablichj.encubator"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        named("commonMain") {
            dependencies {
                api(project(":node"))
                api(compose.runtime)
                api(compose.foundation)
                api(compose.ui)
                api(compose.material)
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }
        named("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        named("androidMain") {
            dependencies {
                implementation("androidx.core:core-ktx:1.9.0")
                //implementation ("com.google.accompanist:accompanist-pager:0.27.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
                implementation( "androidx.compose.material3:material3-window-size-class:1.0.1")
            }
        }
        named("androidTest") {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        named("desktopMain") {
            dependencies {
                api(compose.desktop.common)
                api(compose.materialIconsExtended)
                api(compose.preview)
                api("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
            }
        }
        named("desktopTest")
    }
}


android {
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}
