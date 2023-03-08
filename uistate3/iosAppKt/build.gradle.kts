plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("org.jetbrains.compose")
}

version = "1.0-SNAPSHOT"
//val ktorVersion = extra["ktor.version"]

kotlin {
    // IOS
    iosArm64()
    iosSimulatorArm64()
    cocoapods {
        summary = "UiState3Demo umbrella module"
        homepage = "https://github.com/pablichjenkov/templato"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "UiState3Kt"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation("org.jetbrains.compose.components:components-resources:1.3.0-beta04-dev879")
                implementation(project(":uistate3"))
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
