package me.oleg.tagliber.viewmodels

import androidx.lifecycle.ViewModel
import me.oleg.tagliber.data.Note
import me.oleg.tagliber.data.NoteRepository
import me.oleg.tagliber.utitlies.getCurrentDateTime

class NoteDetailViewModel
internal constructor(
    private val noteRepository: NoteRepository
) :
    ViewModel() {

    fun getNoteById(noteId: Int) =
        noteRepository.getNote(noteId)


    fun save(id: Int, title: String, text: String) {
        if (id == -1) {
            saveNewNote(
                title,
                text
            )
        } else {
            saveChanges(
                id,
                title,
                text
            )
        }
    }

    private fun saveChanges(id: Int, title: String, text: String) {

        noteRepository.updateNote(
            Note(
                noteId = id,
                title = title,
                content = text,
                timeStamp = getCurrentDateTime()
            )
        )
    }


    private fun saveNewNote(title: String, text: String) {

        noteRepository.insertNote(
            Note(
                title = title,
                content = text,
                timeStamp = getCurrentDateTime()
            )
        )
    }

    fun shouldShowExitDialog(noteId: Int, note: Note?): Boolean {
        val item = noteRepository.getNote(noteId)
        return item.value === note
    }

}
