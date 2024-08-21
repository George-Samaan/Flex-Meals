plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.iti.flex_meals"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.iti.flex_meals"
        minSdk = 28
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.base)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //navigation
    implementation ("androidx.navigation:navigation-fragment:2.5.3")
    implementation ("androidx.navigation:navigation-ui:2.5.3")

    //Animation
    implementation ("com.airbnb.android:lottie:3.4.0")
    //loading indicator
    implementation ("com.github.leandroborgesferreira:loading-button-android:2.3.0")


    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation ("com.google.firebase:firebase-auth:19.4.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation ("com.google.android.gms:play-services-auth:19.0.0")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    //youtube
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")

    //Circular image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    //Calendar
    implementation("com.github.prolificinteractive:material-calendarview:2.0.0")

//    implementation ("io.reactivex.rxjava3:rxandroid:3.1.9")
//    implementation ("io.reactivex.rxjava3:rxjava:3.1.9")


}