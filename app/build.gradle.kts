plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "me.oleg.tagliber"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "me.oleg.tagliber"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
//        resConfigs 'en', 'ru', 'fa'
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

//        debug {
//            splits.abi.enable = false
//            splits.density.enable = false
//
//            aaptOptions.cruncherEnabled = false // ain't needed in debug mode
//        }

        release {
            release {
                isMinifyEnabled = false
                //            shrinkResources true
                proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                )
            }
    }
    }


//    splits {
//        density  {
//            enable true
//
//            exclude 'ldpi', 'mdpi'
//
//            compatibleScreens 'normal', 'large', 'xlarge'
//        }
//
//        abi {
//            enable true
//
//            reset()
//
//            include 'armabi-v7a'
//
//            universalApk false
//        }
//    }


    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dep.coreKtx)
    implementation(Dep.lifecycle)
    implementation(Dep.composeActivity)
    implementation(Dep.composeUi)
    implementation(Dep.composeTooling)
    implementation(Dep.materialDesign3)
    implementation(Dep.permissionAccompanist)
    implementation(Dep.hilt)
    ksp(Dep.hiltCompiler)

    implementation(Dep.composeNavigationKtx)

    implementation(Dep.glide)
    ksp(Dep.glideCompiler)

    implementation(Dep.coil)
    implementation(Dep.composeNavigation)
    implementation(Dep.composeNavigationAnimation)
    implementation(Dep.appCompat)
    implementation(Dep.activityKtx)
    implementation(Dep.fragment)
    implementation(Dep.constraintLayout)
    implementation(Dep.cardView)
    implementation(Dep.material)

    implementation(Dep.roomRuntime)
    ksp(Dep.roomCompiler)
    implementation(Dep.roomKtx)
    implementation(Dep.roomTest)

    implementation(Dep.recycler)
    implementation(Dep.recyclerSelection)
    implementation(Dep.workRuntimeKtx)
    implementation(Dep.okhttp)
    implementation(Dep.navigationUiKtx)
    implementation(Dep.navigationFragmentKtx)
    implementation(Dep.pagingRuntimeKtx)

    implementation(Dep.lifecycleViewmodelKtx)
    implementation(Dep.lifecyclerLivedataKtx)

    implementation(Dep.coroutineAndorid)
    implementation(Dep.coroutineCore)

    testImplementation(Dep.junit)
    androidTestImplementation(Dep.junitAndroid)
    androidTestImplementation(Dep.espresso)
    androidTestImplementation(Dep.junitUi)
    debugImplementation(Dep.composeToolUi)
    debugImplementation(Dep.composeUiTest)

}
