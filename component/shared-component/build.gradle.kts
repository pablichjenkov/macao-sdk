plugins {
    kotlin("multiplatform")
    //id("com.android.library")
    id("org.jetbrains.compose")
}

version = "1.0-SNAPSHOT"

kotlin {
    // ANDROID
    /*android()
    android {
        publishLibraryVariants("release", "debug")
    }*/

    // JS
    js(IR) {
        browser()
    }

    // JVM
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material)
                implementation(project(":component"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }

}

/*
android {
    namespace = "com.pablichj.incubator.uistate3"
    compileSdk = 33
    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
        }
    }
    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
*/
