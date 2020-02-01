package me.oleg.taglibro.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.oleg.taglibro.data.model.Note
import me.oleg.taglibro.data.repositry.NoteRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepository: NoteRepository
) : ViewModel() {


    fun find(query: String): LiveData<List<Note>> {
        return  searchRepository.find("%$query%")
    }
}