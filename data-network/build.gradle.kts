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
                // ---- di ------
                implementation(libs.koin.core)
                // ---- ktor ------
                implementation(libs.ktor.core)
                implementation(libs.ktor.client.contentNegotiation)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.resources)
                implementation(libs.ktor.serialization.kotlinx.json)
                // ---- sandwich ------
                implementation(libs.sandwich)
                implementation(libs.sandwich.ktor)
                implementation(libs.sandwich.ktorfit)

                implementation(projects.dataModel)

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

