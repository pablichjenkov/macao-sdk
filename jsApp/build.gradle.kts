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
        val jsMain by getting  {
            dependencies {
                implementation(project(":shared"))
                implementation(project(":component-toolkit"))
                implementation(compose.html.core)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.4")
            }
        }
    }
}
