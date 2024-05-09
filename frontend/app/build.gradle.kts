plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.android.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.zeta.pokemonapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zeta.pokemonapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //ROOM
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    //Retrofit
    implementation(libs.squareup.retrofit2.retrofit)
    implementation(libs.squareup.retrofit2.converter)
    implementation(libs.squareup.okhttp.logging)

    //Paging
    implementation(libs.androidx.paging.runtime)
    testImplementation(libs.androidx.paging.common)

    //Koin
    implementation(libs.io.koin.core)
    implementation(libs.io.koin.android)

    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    //Glide
    implementation(libs.bumptech.glide)
    testImplementation(libs.org.mockito)
    androidTestImplementation(libs.org.mockito)
    testImplementation(libs.junit)
    testImplementation(libs.google.truth)
    androidTestImplementation(libs.google.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}