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
            kotlin.srcDirs("src/commonMain/kotlin")
            dependencies {
                implementation(libs.datetime)
                implementation(libs.napier)
            }
        }
        // Default
//        commonMain.dependencies {
//            //put your multiplatform dependencies here
//            implementation(libs.datetime)
//            implementation(libs.napier)
//        }
//        commonTest.dependencies {
//            implementation(libs.kotlin.test)
//        }

        val androidMain by getting {
            kotlin.srcDirs("src/androidMain/kotlin")
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            kotlin.srcDirs("src/iosMain/kotlin")
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
