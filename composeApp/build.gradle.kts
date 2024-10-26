import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
}

/*compose {
    // Sets a specific JetBrains Compose Compiler version
    kotlinCompilerPlugin.set("1.4.7")
    // Sets a specific Google Compose Compiler version
    // kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:1.4.2")
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=1.8.21")
}*/

kotlin {

    // Android
    androidTarget()

    // IOS
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries {
            framework {
                export(project(":component-toolkit"))
                baseName = "MacaoSdkDemoKt"
                isStatic = true
            }
        }
    }

    // JS
    js(IR) {
        browser()
        binaries.executable()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                        add(project.projectDir.path + "/commonMain/")
                        add(project.projectDir.path + "/wasmJsMain/")
                    }
                }
            }
        }
        binaries.executable()
    }

    // JVM
    jvm()

    sourceSets {
        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)

            // Coroutines core
            implementation(libs.kotlinx.coroutines.core)

            // Macao Libs
            api(project(":component-toolkit"))
            api(project(":macao-sdk-app"))
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        androidMain.dependencies {
            // AndroidX
            implementation(libs.activity.compose)

            // Coroutines
            implementation(libs.kotlinx.coroutines.android)
        }
        jvmMain.dependencies {
            // Compose Desktop Ecosystem
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)

            // Coroutines core
            implementation(libs.kotlinx.coroutines.swing)
        }
        jsMain.dependencies {
            // Coroutines core
            implementation(libs.kotlinx.coroutines.core.js)
        }
    }

}

android {
    namespace = "com.macaosoftware.component.demo"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        applicationId = "com.macaosoftware.component.demo"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeCompiler {
        enableStrongSkippingMode = true
    }
}

compose {
    // Sets a specific JetBrains Compose Compiler version
    /*kotlinCompilerPlugin.set("1.4.7")
    // Sets a specific Google Compose Compiler version
    // kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:1.4.2")
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=1.8.21")*/

    desktop {
        application {
            mainClass = "com.macaosoftware.component.demo.MainKt"
        }
    }

    experimental {
        web.application {}
    }
}

/*compose.desktop {
    application {
        mainClass = "com.macaosoftware.component.demo.MainKt"

        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb)
            packageName = "Component-Toolkit Demo"
            packageVersion = "1.0.0"

            windows {
                menuGroup = "UiState3 Examples"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "18159995-d967-4CD2-8885-77BFA97CFA9F"
            }
        }
    }
}*/
