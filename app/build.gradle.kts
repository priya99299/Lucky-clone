plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "firstapp.example.lipsclone"
    compileSdk = 35

    defaultConfig {
        applicationId = "firstapp.example.lipsclone"
        minSdk = 28
        //noinspection OldTargetApi
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("int", "VERSION_CODE", versionCode.toString())
        buildConfigField("String", "VERSION_NAME", "\"${versionName}\"")

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}


dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.cardview)
    //noinspection UseTomlInstead
    implementation ("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation (libs.gridlayout)
    implementation (libs.material.v1130)
    //noinspection UseTomlInstead
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.logging.interceptor)
    implementation (libs.glide)
    implementation (libs.play.services.maps)
    implementation (libs.osmdroid.android)

    implementation(libs.okhttp)
    implementation(libs.osmdroid.android)
    implementation (libs.play.services.maps)



    //noinspection UseTomlInstead
    implementation ("com.google.android.play:app-update:2.1.0")








    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)




}