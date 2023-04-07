import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {}
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation("com.pablichj:component:0.1.5")
                implementation(project(":amadeus-api"))
                implementation(project(":shared"))
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.pablichj.incubator.amadeus.demo.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Amadeus Demo"
            packageVersion = "1.0.0"
            modules("java.sql")
            modules("java.net.http")

            // val iconsRoot = project.file("../common/src/desktopMain/resources/images")
            windows {
                menuGroup = "Amadeus Examples"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "BF9CDA6A-1391-46D5-9ED5-383D6E68CCEB"
            }
            macOS {
                // Use -Pcompose.desktop.mac.sign=true to sign and notarize.
                bundleID = "com.pablichj.incubator.amadeus.demo"
                // iconFile.set(iconsRoot.resolve("icon-mac.icns"))
            }
            linux {
                // iconFile.set(iconsRoot.resolve("icon-linux.png"))
            }
            buildTypes.release {
                proguard {
                    configurationFiles.from(project.file("compose-desktop.pro"))
                }
            }
        }
    }
}

/*compose.desktop {
    application {
        mainClass = "com.pablichj.incubator.amadeus.demo.MainKt"

        nativeDistributions {
            targetFormats(org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb)
            packageName = "Hello World"
            packageVersion = "1.0.0"

            windows {
                menuGroup = "UiState3 Examples"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "18159995-d967-4CD2-8885-77BFA97CFA9F"
            }
        }
    }
}*/
