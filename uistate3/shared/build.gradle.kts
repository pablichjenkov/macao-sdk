import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("maven-publish")
}

group = "com.pablichj"
version = "0.1.0"

fun String.dasherize() = fold("") {acc, value ->
    if (value.isUpperCase()) {
        "$acc-${value.toLowerCase()}"
    } else {
        "$acc$value"
    }
}

fun makeArtifactId(name: String) =
    if ("kotlinMultiplatform" in name) {
        "state3"
    } else {
        "state3-${name.dasherize()}"
    }

afterEvaluate {
    configure<PublishingExtension> {
        publications.all {
            val mavenPublication = this as? MavenPublication
            mavenPublication?.artifactId = makeArtifactId(name)
        }
    }
}

configure<PublishingExtension> {
    publications {
        withType<MavenPublication> {
            //groupId = "com.meowbox.fourpillars"
            artifactId = makeArtifactId(name)
            //version
        }
    }
}

/*publishing {
    publications {
        println("Pablo: publication = $name")
        withType<MavenPublication> {
            //groupId = "com.meowbox.fourpillars"
            artifactId = makeArtifactId(name)
            //version
        }
        *//*create<MavenPublication>("state3x") {
            groupId = "org.gradle.sample"
            artifactId = "state3-desktop"
            version = "1.1"

            from(components["java"])
        }*//*
    }
}*/

kotlin {
    // ANDROID
    android()
    android {
        publishLibraryVariants("release", "debug")
    }

    // IOS
    //ios()
    //iosSimulatorArm64()
    cocoapods {
        summary = "Shared code for the UiState3 example"
        homepage = "https://github.com/pablichjenkov/uistate3b"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    val xcf = XCFramework()
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            xcf.add(this)
        }
    }

    // JVM
    jvm("desktop")

    // JS
    js(IR) {
        browser()
    }

    // MACOS
    /*macosX64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    macosArm64 {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }*/

    sourceSets {
        // COMMON
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.runtime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        // ANDROID
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.6.1")
                implementation ("com.google.accompanist:accompanist-pager:0.28.0")
                implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")
                implementation ("androidx.window:window:1.0.0")
                implementation( "androidx.compose.material3:material3-window-size-class:1.0.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }

        // IOS
        /*val iosMain by getting
        val iosTest by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(iosTest)
        }*/

        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        // JVM
        val desktopMain by getting

        // JS
        val jsMain by getting

        // MACOS
        /*val macosMain by creating {
            dependsOn(commonMain)
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }*/
    }

    /*kotlinArtifacts {
        Native.Library("state3") {
            target = iosArm64 // Define your target instead
            modes(DEBUG, RELEASE)
            // Binary configuration
        }
    }*/

}

android {
    namespace = "com.pablichj.incubator.uistate3"
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