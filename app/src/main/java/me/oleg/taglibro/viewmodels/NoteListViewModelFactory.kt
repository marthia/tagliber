package me.oleg.taglibro.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.oleg.taglibro.data.NoteRepository

class NoteListViewModelFactory(
    private val repository: NoteRepository,
    private val context: Application
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        NoteViewModel(repository, context) as T
}