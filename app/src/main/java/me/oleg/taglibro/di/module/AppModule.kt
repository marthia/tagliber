package me.oleg.taglibro.di.module

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import me.oleg.taglibro.R
import me.oleg.taglibro.utitlies.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRequestOptions() = RequestOptions
        .placeholderOf(R.drawable.white_background)
        .error(R.drawable.white_background)

    @Singleton
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ) = Glide
        .with(application)
        .setDefaultRequestOptions(requestOptions)

//    @Singleton
//    @Provides
//    fun provideFarzanehProfileImage(
//
//        application: Application
//
//    ): Drawable {
//        return ContextCompat.getDrawable(application, R.drawable.farzaneh)!!
//    }

}
