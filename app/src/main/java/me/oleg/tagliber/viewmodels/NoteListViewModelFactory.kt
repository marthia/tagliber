package me.oleg.tagliber.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.oleg.tagliber.data.NoteRepository

class NoteListViewModelFactory(
    private val repository: NoteRepository,
    private val context: Application
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        NoteViewModel(repository, context) as T
}