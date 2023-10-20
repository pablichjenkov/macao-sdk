plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm()
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(project(":shared"))
                implementation(project(":component-toolkit"))
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(compose.material3)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")
            }
        }
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
