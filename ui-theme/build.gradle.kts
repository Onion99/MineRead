plugins {
    id("android-library-convention")
    id("kotlin-library-convention")
    id("org.jetbrains.compose")
    alias(libs.plugins.composeCompiler)
}
android {
    namespace = "com.onion.ui.theme"
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.compose.adaptive)
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

