plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Firebase Plugin
}

android {
    namespace = "com.example.ogacorp.malaika"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.ogacorp.malaika"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Standard Android Dependencies
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Firebase Dependencies (BOM handles versions)
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Google Sign-In (SSO) Dependency
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Glide (For loading images from URLs in the future)
    implementation("com.github.bumptech.glide:glide:4.16.0")

    /// Testing Dependencies
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation("androidx.test:core:1.7.0")
}