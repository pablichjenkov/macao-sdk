plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.dokka")
    id("maven-publish")
    signing
}

group = "io.github.pablichjenkov"
version = "0.1.14"// in stage
val mavenCentralUser = extra["mavenCentral.user"] as String
val mavenCentralPass = extra["mavenCentral.pass"] as String

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

/*tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
    moduleName.set("component")
    cacheRoot.set(file("default"))
    suppressObviousFunctions.set(false)
    offlineMode.set(true)
}*/

// Configure Dokka
tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    // custom output directory
    outputDirectory.set(buildDir.resolve("dokka"))
    moduleName.set("component")
    suppressObviousFunctions.set(false)
    offlineMode.set(true)
    /*dokkaSourceSets {
        named("customNameMain") { // The same name as in Kotlin Multiplatform plugin, so the sources are fetched automatically
            includes.from("packages.md", "extra.md")
            samples.from("samples/basic.kt", "samples/advanced.kt")
        }

        register("differentName") { // Different name, so source roots must be passed explicitly
            displayName.set("JVM")
            platform.set(org.jetbrains.dokka.Platform.jvm)
            sourceRoots.from(kotlin.sourceSets.getByName("jvmMain").kotlin.srcDirs)
            sourceRoots.from(kotlin.sourceSets.getByName("commonMain").kotlin.srcDirs)
        }
    }*/
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

publishing {
    repositories {
        maven {
            name = "Central"
            //setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            // setUrl("https://s01.oss.sonatype.org/content/repositories/releases/")
            credentials {
                username = mavenCentralUser
                password = mavenCentralPass
            }
        }
    }
    publications {
        println("publication = $name")
        withType<MavenPublication> {
            groupId = group as String
            artifactId = "templato-components"//makeArtifactId(name)
            version
            artifact(javadocJar)
            pom {
                val projectGitUrl =
                    "https://github.com/pablichjenkov/templato/tree/master/component"
                name.set(rootProject.name)
                description.set(
                    "Reusable components to be used in compose multiplatform."
                )
                url.set(projectGitUrl)
                inceptionYear.set("2023")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("pablichjenkov")
                    }
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("$projectGitUrl/issues")
                }
                scm {
                    connection.set("scm:git:$projectGitUrl")
                    developerConnection.set("scm:git:$projectGitUrl")
                    url.set(projectGitUrl)
                }
            }
        }
        /*create<MavenPublication>("state3x") {
            groupId = "org.gradle.sample"
            artifactId = "state3-desktop"
            version = "1.1"

            from(components["java"])
        }*/
    }
}

signing {
    sign(publishing.publications)
}

kotlin {
    // ANDROID
    android()
    android {
        publishLibraryVariants("release", "debug")
    }

    // IOS
    iosArm64()
    iosSimulatorArm64()

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
                implementation("androidx.activity:activity-compose:1.7.0")
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

        // WASM
        /*val wasmMain by getting
        val wasmTest by getting
        */

        // JVM
        val desktopMain by getting
    }

}

android {
    namespace = "com.pablichj.templato.component"
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
