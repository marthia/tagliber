package me.oleg.taglibro.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.oleg.taglibro.base.BaseApplication
import me.oleg.taglibro.di.module.ActivityBindingModule
import me.oleg.taglibro.di.module.AppModule
import me.oleg.taglibro.di.module.ContextModule
import me.oleg.taglibro.di.module.ViewModelFactoryModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ContextModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        AppModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {


    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application) : Builder
        fun build() : AppComponent
    }
}