import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

kotlin {
    androidTarget()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "MacaoSdk"
            isStatic = true
        }
    }

    js(IR) {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "MacaoSdkKoin"
        browser()
        binaries.library()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

            // Macao Libs
            api(project(":component-toolkit"))
        }
        commonTest.dependencies {
            // implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
        }
        jvmMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.0")
        }
        jsMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.8.0")
        }
    }
}

android {
    namespace = "com.macaosoftware.app"
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
