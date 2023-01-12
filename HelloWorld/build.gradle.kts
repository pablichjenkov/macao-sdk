plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    // ANDROID
    android()


    // IOS
    // Workaround for an issue:
    //    https://youtrack.jetbrains.com/issue/KT-53561/Invalid-LLVM-module-inlinable-function-call-in-a-function-with-debug-info-must-have-a-dbg-location
    // Compose compiler produces nodes without line information sometimes that provokes Kotlin native compiler to report errors.
    // TODO: remove workaround when switch to Kotlin 1.8
    val disableKonanVerification = "-Xverify-compiler=false"

    iosArm64("uikitArm64") {
        binaries {
            executable() {
                entryPoint = "com.pablichj.incubator.uistate3.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics",
                    disableKonanVerification
                )
            }
        }
    }

    iosX64("uikitX64") {
        binaries {
            executable() {
                entryPoint = "com.pablichj.incubator.uistate3.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics",
                    disableKonanVerification
                )
            }
        }
    }

    // JVM
    jvm()

    // JS
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        // COMMON
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }

        // ANDROID
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.6.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
            }
        }

        // JVM
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
            }
        }

        // JS
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
            }
        }
    }

}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb)
            packageName = "Hello World"
            packageVersion = "1.0.0"

            windows {
                menuGroup = "Compose Examples"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "18159995-d967-4CD2-8885-77BFA97CFA9F"
            }
        }
    }
}

compose.experimental {
    web.application {}
}

android {
    namespace = "com.pablichj.incubator.uistate3.example.helloWorld"
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        applicationId = "com.pablichj.incubator.uistate3.example.helloWorld"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}