package me.oleg.taglibro.ui.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.oleg.taglibro.ui.detail.NoteDetailFragment
import me.oleg.taglibro.ui.list.NoteListFragment

@Module
abstract class MainFragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun provideListFragment(): NoteListFragment?

    @ContributesAndroidInjector
    abstract fun provideDetailsFragment(): NoteDetailFragment?
}