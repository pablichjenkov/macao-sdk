plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    kotlin("multiplatform") apply false
    id("org.jetbrains.kotlin.plugin.compose") apply false
    id("org.jetbrains.compose") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false

    id("org.jetbrains.dokka") apply false
}

allprojects {
    afterEvaluate {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
            compilerOptions {
                with(optIn) {
                    add("kotlin.experimental.ExperimentalObjCName")
                    add("androidx.compose.ui.ExperimentalComposeUiApi")
                    add("androidx.compose.ui.window.ExperimentalComposeUiApi")
                    add("androidx.compose.material3.ExperimentalMaterial3Api")
                    add("androidx.compose.foundation.layout.ExperimentalLayoutApi")
                    add("org.jetbrains.compose.resources.ExperimentalResourceApi")
                    add("kotlinx.serialization.ExperimentalSerializationApi")
                    add("androidx.compose.foundation.ExperimentalFoundationApi")
                }
            }
        }
    }
}
