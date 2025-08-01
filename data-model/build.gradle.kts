import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("android-library-convention")
    id("kotlin-library-convention")
}
android {
    namespace = "com.onion.data.model"
    buildFeatures {
        compose = false
    }
}

kotlin {

    sourceSets {
        named("commonMain") {
            dependencies {
            }
        }
        named("androidMain") {
            dependencies {
                //implementation(libs.ktor.client.android)
            }
        }
        named("desktopMain") {
            dependencies {
                //implementation(libs.ktor.client.java)
            }
        }
    }
}

