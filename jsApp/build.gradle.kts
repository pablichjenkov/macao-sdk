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
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        jsMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(project(":shared"))
            implementation(project(":component-toolkit"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.7.3")
        }
    }
}
