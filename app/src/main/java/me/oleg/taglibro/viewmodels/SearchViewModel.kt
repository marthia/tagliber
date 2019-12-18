package me.oleg.taglibro.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.oleg.taglibro.data.Note
import me.oleg.taglibro.data.NoteRepository

class SearchViewModel(
    private val searchRepository: NoteRepository
) : ViewModel() {


    fun find(query: String): LiveData<List<Note>> {
        return  searchRepository.find("%$query%")
    }
}