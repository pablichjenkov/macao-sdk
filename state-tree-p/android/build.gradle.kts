plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group = "com.pablichj.encubator"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation ("com.google.accompanist:accompanist-pager:0.27.1")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.27.1")
    implementation ("androidx.window:window:1.0.0")
    implementation( "androidx.compose.material3:material3-window-size-class:1.0.1")
}

android {
    namespace = "com.pablichj.encubator.node"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.pablichj.encubator.node.example"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}