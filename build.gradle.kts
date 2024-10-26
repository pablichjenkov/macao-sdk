plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.dokka).apply(false)
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
