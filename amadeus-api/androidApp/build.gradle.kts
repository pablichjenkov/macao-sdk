plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation("com.pablichj:component:0.1.5")
                implementation(project(":amadeus-api"))
                implementation(project(":shared"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material)
                implementation("androidx.activity:activity-compose:1.7.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
            }
        }
    }
}

android {
    namespace = "com.pablichj.incubator.amadeus.demo"
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        applicationId = "com.pablichj.incubator.amadeus.demo"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packagingOptions {
        resources {
            // excludes += "/META-INF/{AL2.0,LGPL2.1}"
            pickFirsts.apply {
                add("META-INF/kotlinx_coroutines_core.version")
                add("META-INF/INDEX.LIST")
            }
        }
    }
}