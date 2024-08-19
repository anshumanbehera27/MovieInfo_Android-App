plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.anshuman.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.anshuman.myapplication"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // add the dependency for Lottiefiles
    implementation (libs.lottie)

    // page navigation dependecy
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")


    // add the dependecy for viewpager
    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    // add the depedency for floting navagating Bar
    implementation ("com.github.ismaeldivita:chip-navigation-bar:1.4.0")
    implementation("com.google.android.material:material:1.9.0")


    // add for the blurView
    implementation ("com.github.Dimezis:BlurView:version-2.0.5")

    // json dependecy
    implementation ("com.google.code.gson:gson:2.11.0")

    // Glide is use to convert the one url to image
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    // retrofit dependency
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // for api request
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


}