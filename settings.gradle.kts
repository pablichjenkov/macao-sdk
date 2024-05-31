rootProject.name = "macao-sdk"
include(":component-toolkit")
include(":macao-sdk-app")
include(":composeApp")

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }
    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String
        val dokkaVersion = extra["dokkaVersion"] as String

        kotlin("multiplatform").version(kotlinVersion)
        id("org.jetbrains.kotlin.plugin.compose").version(kotlinVersion)
        id("org.jetbrains.compose").version(composeVersion)
        kotlin("android").version(kotlinVersion)
        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.dokka").version(dokkaVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
    }
}
