import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("android-library-convention")
    id("kotlin-library-convention")
    alias(libs.plugins.kotlin.serialization)
}
android {
    namespace = "com.onion.data.model"
    buildFeatures {
        compose = false
    }
}

kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.serialization)
            }
        }
    }
}

