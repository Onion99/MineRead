import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    id("android-library-convention")
    id("kotlin-library-convention")
}
android {
    namespace = "com.onion.data.network"
    buildFeatures {
        compose = false
    }
}

kotlin {

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(libs.koin.core)
                // ---- sandwich ------
                implementation(libs.sandwich)
                implementation(libs.sandwich.ktor)
                implementation(libs.sandwich.ktorfit)
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

