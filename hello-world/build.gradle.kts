import java.net.URI

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.compose") apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
        /** This is your CloudRepo Repository connection information
         * - for READing/DOWNLOADing purposes */
        maven {
            name = "pablichj"
            url = URI.create("https://pablichjenkov.mycloudrepo.io/public/repositories/pablichj")
        }
    }
}
