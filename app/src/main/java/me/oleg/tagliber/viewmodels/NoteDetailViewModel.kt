package me.oleg.tagliber.viewmodels

import androidx.lifecycle.ViewModel
import me.oleg.tagliber.data.Note
import me.oleg.tagliber.data.NoteRepository

class NoteDetailViewModel
internal constructor(
    private val noteRepository: NoteRepository
) :
    ViewModel() {

    fun getNoteById(noteId: Int) =
        noteRepository.getNote(noteId)

    private fun insertOrUpdate(note:Note) {
        noteRepository.insertNote(note)
    }

}
