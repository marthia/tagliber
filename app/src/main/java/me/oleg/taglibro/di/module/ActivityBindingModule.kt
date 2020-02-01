package me.oleg.taglibro.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.oleg.taglibro.ui.main.MainActivity
import me.oleg.taglibro.ui.main.MainFragmentBindingModule

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainFragmentBindingModule::class])
    abstract fun bindMainActivity() : MainActivity
}