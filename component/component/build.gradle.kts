plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("maven-publish")
}

group = "com.pablichj"
version = "0.1.0.5"

/*
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
*/

/*publishing {
    publications {
        println("publication = $name")
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
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    // Do not include the cocoapod plugin here, it forces all composables to be internal for iOS
    // target to compile.
//    cocoapods {
//        summary = "Shared code for the UiState3 example"
//        homepage = "https://github.com/pablichjenkov/uistate3"
//        ios.deploymentTarget = "14.1"
//        podfile = project.file("../iosApp/Podfile")
//        framework {
//            baseName = "uistate3"
//            isStatic = true
//        }
//        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
//    }

    //Comment it out if using cocoapods, and don't want to use xcframeworks directly
    /*listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }*/

    // JS
    js(IR) {
        browser()
    }

    // WASM, once kotlin 1.8.20 is out. Although I believe this should go in the jsApp module not
    // in the library. Perhaps can go here without the binaries.executable() statement
    /*wasm {
        binaries.executable()
        browser {}
    }*/

    // JVM
    jvm("desktop")

    sourceSets {
        // COMMON
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material)
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
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
        val androidUnitTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val androidInstrumentedTest by getting

        // IOS
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        // JS
        val jsMain by getting

        // WASM
        /*val wasmMain by getting
        val wasmTest by getting
        */

        // JVM
        val desktopMain by getting
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