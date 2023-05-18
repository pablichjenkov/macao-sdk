plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.github.gmazzo.buildconfig")
}

group = "com.pablichj"
version = "0.1.0"

kotlin {
    // ANDROID
    android()

    // IOS
    iosArm64()
    iosSimulatorArm64()
    cocoapods {
        summary = "Shared code for the Hotel Booking template"
        homepage = "https://github.com/pablichjenkov/templato"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "HotelBookingKt"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    // JS
    js(IR) {
        browser()
    }

    // JVM
    jvm("desktop")

    sourceSets {
        // COMMON
        val commonMain by getting {
            dependencies {
                api("io.github.pablichjenkov:templato-components:0.1.22")
                implementation("io.github.pablichjenkov:amadeus-api:0.1.8")
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
                implementation("androidx.activity:activity-compose:1.7.1")
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val androidInstrumentedTest by getting

        // IOS
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

        // JS
        val jsMain by getting

        // JVM
        val desktopMain by getting
    }

}

android {
    namespace = "com.pablichj.incubator.uistate3.example.hotelBooking"
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

buildConfig {
    useKotlinOutput { internalVisibility = true }
    packageName("com.pablichj.incubator.uistate3.example.hotelBooking.shared")

    val amadeusApiKey = extra["amadeus.apiKey"] as String
    require(amadeusApiKey.isNotEmpty()) {
        "Register your api key from amadeus and place it in local.properties as `amadeus.apiKey`"
    }

    val amadeusApiSecret = extra["amadeus.apiSecret"] as String
    require(amadeusApiKey.isNotEmpty()) {
        "Register your api secret from amadeus and place it in local.properties as `amadeus.apiSecret`"
    }

    buildConfigField(
        "String",
        "AMADEUS_API_KEY", amadeusApiKey
    )

    buildConfigField(
        "String",
        "AMADEUS_API_SECRET", amadeusApiSecret
    )

}