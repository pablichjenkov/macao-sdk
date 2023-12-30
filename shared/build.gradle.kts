plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

version = (findProperty("component-toolkit.version") as? String).orEmpty()

/*compose {
    // Sets a specific JetBrains Compose Compiler version
    kotlinCompilerPlugin.set("1.4.7")
    // Sets a specific Google Compose Compiler version
    // kotlinCompilerPlugin.set("androidx.compose.compiler:compiler:1.4.2")
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=1.8.21")
}*/

kotlin {
    // IOS
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries {
            framework {
                baseName = "ComponentDemoKt"
                isStatic = true
            }
        }
    }

    // JS
    js(IR) {
        browser()
    }

    // JVM
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.material3)
            api(project(":macao-sdk-di-koin"))
            api(project(":macao-sdk-di-manual"))
            implementation("org.jetbrains.compose.components:components-resources:1.5.10")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }

}
