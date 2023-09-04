import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

val keystoreProperties = Properties().apply {
    val secretPropertiesFile = rootProject.file("secret.properties")
    load(FileInputStream(secretPropertiesFile))
}

android {

    val buildNumber by extra { 11 }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = if (baseName == "release") "jReader_v${versionName}.apk"
                else "jReader-dev_v${versionName}.apk"
                output.outputFileName = outputFileName
            }
    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties.getProperty("KEYSTORE_FILE"))
            storePassword = keystoreProperties.getProperty("KEYSTORE_PASSWORD")
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
        }
    }

    compileSdk = 34

    defaultConfig {
        applicationId = "com.compose.jreader"
        minSdk = 27
        targetSdk = 34
        versionCode = buildNumber
        versionName = "1.0"
        buildConfigField("String", "BASE_URL", "\"https://www.googleapis.com/books/v1/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resourceConfigurations += listOf("en", "ar")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }


    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    namespace = "com.compose.jreader"

    task("incrementBuildNumber") {
        doLast {
            val buildFileText = buildFile.readText()
            val newBuildFileText = buildFileText.replaceFirst(
                "val buildNumber by extra { $buildNumber }",
                "val buildNumber by extra { ${buildNumber + 1} }"
            )
            buildFile.writeText(newBuildFileText)
        }
    }

}

dependencies {

    val composeBom = platform("androidx.compose:compose-bom:2023.08.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material:material")

    // Material Icons
    implementation("androidx.compose.material:material-icons-extended")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Navigation compose
    implementation("androidx.navigation:navigation-compose:2.7.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))

    // Firebase Auth and Firestore
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    //Dagger - Hilt
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.7")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.7")

    // JSON Converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Navigation animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.23.0")

    // Constraint layout compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Compose runtime dependency for collectAsStateWithLifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

}