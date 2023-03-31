plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("org.jetbrains.compose")
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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation("org.jetbrains.compose.components:components-resources:1.4.0-alpha01-dev942")
                implementation(project(":core"))
                implementation("com.pablichj:component:0.1.5-ios")
            }
        }

        // IOS
        //val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            //iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        /*val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }*/

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
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "AMADEUS_API_KEY", amadeusApiKey
        )

        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "AMADEUS_API_SECRET", amadeusApiSecret
        )

    }
}
