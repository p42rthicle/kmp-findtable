plugins {
    // Default
//    alias(libs.plugins.kotlinMultiplatform)
//    alias(libs.plugins.androidLibrary)

    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.datetime)
                implementation(libs.napier)
            }
        }
//        commonMain.dependencies {
//            implementation(libs.datetime)
//            implementation(libs.napier)
//        }
        // Default
//        commonTest.dependencies {
//            implementation(libs.kotlin.test)
//        }

        val androidMain by getting {
            dependencies {
                // Android-specific dependencies if needed
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "kmp.parth.findtime"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
