plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("app.cash.sqldelight")
}

group = "com.pablichj"
version = "0.1.0"

kotlin {
    // ANDROID
    android()

    // IOS
    iosArm64()
    iosSimulatorArm64()
    /*ios {
        binaries {
            framework {
                baseName = 'shared'
            }
        }
    }*/

    // JS
    js(IR) {
        browser()
    }

    // JVM
    jvm("desktop")

    sourceSets.forEach {
        it.dependencies {
            implementation(project.dependencies.enforcedPlatform("io.ktor:ktor-bom:2.2.4"))
        }
    }

    sourceSets {
        // COMMON
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
                implementation("io.ktor:ktor-client-core")
                implementation("io.ktor:ktor-client-content-negotiation")
                implementation("io.ktor:ktor-serialization-kotlinx-json")
                implementation("io.ktor:ktor-client-logging")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation ("ch.qos.logback:logback-classic:1.3.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
            // kotlinx-coroutines-test = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-test", version.ref = "coroutines" }
        }

        // ANDROID
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android")
                implementation("app.cash.sqldelight:android-driver:2.0.0-alpha05")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val androidInstrumentedTest by getting

        // IOS
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-ios")
                implementation("app.cash.sqldelight:native-driver:2.0.0-alpha05")
            }
        }
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        // JS
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js")
                implementation("app.cash.sqldelight:sqljs-driver:2.0.0-alpha05")
                implementation(npm("sql.js", "1.6.2"))
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
                // implementation(npm("@js-joda/timezone", "2.3.0"))
            }
        }

        // JVM
        val desktopMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-jvm")
                implementation("io.ktor:ktor-client-java")
                implementation("app.cash.sqldelight:sqlite-driver:2.0.0-alpha05")
            }
        }
    }

}

android {
    namespace = "com.pablichj.incubator.amadeus"
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

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.pablichj.incubator.amadeus")
        }
    }
}