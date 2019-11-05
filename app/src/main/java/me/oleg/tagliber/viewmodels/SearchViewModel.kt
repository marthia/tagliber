package me.oleg.tagliber.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.oleg.tagliber.data.Note
import me.oleg.tagliber.data.NoteRepository

class SearchViewModel(
    private val searchRepository: NoteRepository
) : ViewModel() {


    fun find(query: String): LiveData<List<Note>> {
        return  searchRepository.find("%$query%")
    }
}