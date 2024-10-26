plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.dokka)
    id("maven-publish")
    id("signing")
}

group = "io.github.pablichjenkov"
version = libs.versions.macaoComponentToolkit.get()
val mavenCentralUser = (findProperty("mavenCentral.user") as? String).orEmpty()
val mavenCentralPass = (findProperty("mavenCentral.pass") as? String).orEmpty()

// Configure Dokka
tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    // custom output directory
    outputDirectory.set(buildDir.resolve("dokka"))
    moduleName.set("component")
    suppressObviousFunctions.set(false)
    offlineMode.set(true)
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

signing {
    sign(publishing.publications)
}

publishing {
    repositories {
        maven {
            name = "Central"
            // setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            // setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            setUrl("https://s01.oss.sonatype.org/content/repositories/releases/")
            credentials {
                username = mavenCentralUser
                password = mavenCentralPass
            }
        }
    }
    publications {
        withType<MavenPublication> {
            groupId = group as String
            artifactId = "macao-sdk-app"
            version
            artifact(javadocJar)
            pom {
                val projectGitUrl = "https://github.com/pablichjenkov/macao-sdk"
                name.set(rootProject.name)
                description.set(
                    "Compose Multiplatform Application Microframework"
                )
                url.set(projectGitUrl)
                inceptionYear.set("2023")
                licenses {
                    license {
                        name.set("The Unlicense")
                        url.set("https://unlicense.org")
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
    }
}

// Workaround for gradle issue: https://youtrack.jetbrains.com/issue/KT-46466
val signingTasks = tasks.withType<Sign>()
tasks.withType<AbstractPublishToMaven>().configureEach {
    dependsOn(signingTasks)
}

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "MacaoSdkApp"
            isStatic = true
        }
    }

    js(IR) {
        browser()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "MacaoSdkApp"
        browser()
        binaries.library()
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.foundation)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Macao Libs
            implementation(project(":component-toolkit"))
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

android {
    namespace = "com.macaosoftware.app"
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        compileSdk = libs.versions.androidCompileSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeCompiler {
        enableStrongSkippingMode = true
    }
}
