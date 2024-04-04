import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

compose {
    // Sets a specific JetBrains Compose Compiler version
    /*
        kotlinCompilerPlugin.set("1.4.7")
        // Sets a specific Google Compose Compiler version
        // kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:1.4.2")
        kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=1.8.21")
    */

    experimental {
        web.application {}
    }
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "wasmApp"
        browser {
            commonWebpackConfig {
                outputFileName = "wasmApp.js"
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

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(project(":shared"))
            implementation(project(":macao-sdk-di-koin"))
            implementation(project(":macao-sdk-di-manual"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
        }
    }

}
