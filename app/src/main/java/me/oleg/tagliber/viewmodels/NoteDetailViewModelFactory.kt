package me.oleg.tagliber.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.oleg.tagliber.data.NoteRepository

class NoteDetailViewModelFactory(
    private val repository: NoteRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        NoteDetailViewModel(repository) as T
}