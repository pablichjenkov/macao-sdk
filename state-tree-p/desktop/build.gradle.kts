import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.pablichj.encubator"
version = "1.0-SNAPSHOT"

kotlin {
    jvm {}
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":common"))
            }
        }
        named("jvmTest")
    }
}

compose.desktop {
    application {
        mainClass = "com.pablichj.encubator.node.example.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "node-nav-demo"
            packageVersion = "1.0.0"
        }
    }
}
