package me.oleg.taglibro.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import me.oleg.taglibro.di.util.ViewModelKey
import me.oleg.taglibro.viewmodels.NoteDetailViewModel
import me.oleg.taglibro.viewmodels.NoteViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel::class)
    abstract fun bindListViewModel(listViewModel: NoteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteDetailViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: NoteDetailViewModel): ViewModel

}