import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("native.cocoapods")
    id("com.codingfeline.buildkonfig")
}

version = "1.0-SNAPSHOT"

kotlin {
    // IOS
    iosArm64()
    iosSimulatorArm64()
    cocoapods {
        summary = "Amadeus API umbrella module"
        homepage = "https://github.com/pablichjenkov/templato"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "AmadeusDemoKt"
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
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material)
                implementation(project(":amadeus-api"))
                implementation("com.pablichj:component:0.1.5")
                implementation("org.jetbrains.compose.components:components-resources:1.4.0-alpha01-dev942")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
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
    }

}

buildkonfig {
    packageName = "com.pablichj.incubator.amadeus.demo"

    defaultConfigs {
        val amadeusApiKey = extra["amadeus.apiKey"] as String
        require(amadeusApiKey.isNotEmpty()) {
            "Register your api key from amadeus and place it in local.properties as `amadeus.apiKey`"
        }

        val amadeusApiSecret = extra["amadeus.apiSecret"] as String
        require(amadeusApiKey.isNotEmpty()) {
            "Register your api secret from amadeus and place it in local.properties as `amadeus.apiSecret`"
        }

        buildConfigField(
            FieldSpec.Type.STRING,
            "AMADEUS_API_KEY", amadeusApiKey
        )

        buildConfigField(
            FieldSpec.Type.STRING,
            "AMADEUS_API_SECRET", amadeusApiSecret
        )

    }
}