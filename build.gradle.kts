group "com.pablichj.encubator"
version "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false version "7.3.1"
    id("com.android.library") apply false version "7.3.1"
    id("org.jetbrains.compose") apply false
}