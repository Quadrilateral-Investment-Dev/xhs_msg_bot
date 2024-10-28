plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.xiaohongshuautomation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.xiaohongshuautomation"
        minSdk = 23
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // AndroidX and Material dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.uiautomator)
    implementation(libs.monitor)
    implementation(libs.ext.junit)

    // UI testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // PostgreSQL JDBC Driver for database connectivity
    implementation("org.postgresql:postgresql:42.2.5")

    // UI Automator for Android automation
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.2.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3") // or latest version
    implementation ("org.apache.commons:commons-csv:1.8") // Use the latest version
}
