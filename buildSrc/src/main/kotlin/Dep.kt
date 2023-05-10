object Dep {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.androidCoreKtx}" }
    val lifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidLifecycle}" }
    val composeActivity by lazy { "androidx.activity:activity-compose:${Versions.composeActivity}" }
    val composeUi by lazy { "androidx.compose.ui:ui:${Versions.compose}" }
    val composeTooling by lazy { "androidx.compose.ui:ui-tooling-preview:${Versions.compose}" }
    val materialDesign3 by lazy { "androidx.compose.material3:material3:${Versions.material3}" }
    val permissionAccompanist by lazy { "com.google.accompanist:accompanist-permissions:${Versions.accompanist}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
    val coil by lazy { "io.coil-kt:coil-compose:${Versions.coil}" }
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val junitAndroid by lazy { "androidx.test.ext:junit:${Versions.androidJunit}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    val junitUi by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.compose}" }
    val composeToolUi by lazy { "androidx.compose.ui:ui-tooling:${Versions.compose}" }
    val composeUiTest by lazy { "androidx.compose.ui:ui-test-manifest:${Versions.compose}" }
    val composeNavigation by lazy { "androidx.navigation:navigation-compose:${Versions.composeNavigation}" }
    val composeNavigationAnimation by lazy { "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}" }
    val composeNavigationKtx by lazy { "androidx.navigation:navigation-runtime-ktx:${Versions.composeNavigationKtx}" }
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
    val activityKtx by lazy { "androidx.activity:activity-ktx:${Versions.activityKtx}" }
    val fragment by lazy { "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val cardView by lazy { "androidx.cardview:cardview:${Versions.cardView}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val swipeRefresh by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}" }

    // optional - Kotlin Extensions and Coroutines support for Room
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val roomTest by lazy { "androidx.room:room-testing:${Versions.room}" }
    val recycler by lazy { "androidx.recyclerview:recyclerview:${Versions.recycler}" }
    val recyclerSelection by lazy { "androidx.recyclerview:recyclerview-selection:${Versions.recyclerSelection}" }
    val workRuntimeKtx by lazy { "androidx.work:work-runtime-ktx:${Versions.workRuntime}" }
    val okhttp by lazy { "com.squareup.okhttp:okhttp:${Versions.okhttp}" }
    val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }
    val navigationFragmentKtx by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val pagingRuntimeKtx by lazy { "androidx.paging:paging-runtime-ktx:${Versions.paging}" }
    val lifecycleViewmodelKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodel}" }
    val lifecyclerLivedataKtx by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleViewmodel}" }
    val coroutineAndorid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val coroutineCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val glideCompiler by lazy { "com.github.bumptech.glide:compiler:${Versions.glide}" }
    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
}