plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)

    id("com.google.gms.google-services")

}
val tomtomApiKey: String by project


android {
    namespace = "com.example.threaveling"
    compileSdk = 35

    defaultConfig {

        applicationId = "com.example.threaveling"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildFeatures {
            buildConfig = true
        }
        buildTypes.configureEach {
            buildConfigField("String", "TOMTOM_API_KEY","\"$tomtomApiKey\"")
        }
        packaging {
            jniLibs.pickFirsts.add("lib/**/libc++_shared.so")

            resources {
                excludes += "google/protobuf/field_mask.proto"
            }
        }
        configurations.all {
            //exclude (group= "com.google.protobuf", module= "protobuf-javalite")
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        jniLibs.pickFirsts.add("lib/**/libc++_shared.so")

    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.ui.test.android)
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")
    implementation(libs.transport.runtime)
    implementation(libs.androidx.foundation.layout.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    //androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
        //Firestore
    implementation(platform(libs.firebase.bom.v3380))
    implementation(libs.com.google.firebase.firebase.firestore)

    //Mapbox
    implementation(libs.place.autocomplete.v270)
    implementation (libs.autofill)
    implementation(libs.mapbox.search.android.v270)
    implementation (libs.mapbox.search.android.ui)

    //Cloudinary
    implementation(libs.kotlin.url.gen)
    implementation("com.cloudinary:kotlin-uploader:1.10.0")
    implementation("com.cloudinary:kotlin-transformation-builder-sdk:1.5.1")

}