import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.codingfeline.buildkonfig")
}

version = "1.0-SNAPSHOT"

kotlin {

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
                implementation(project(":core"))
                implementation("com.pablichj:component:0.1.5")
                implementation("org.jetbrains.compose.components:components-resources:1.4.0-alpha01-dev942")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
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