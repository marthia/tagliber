package me.oleg.taglibro.viewmodels

import androidx.lifecycle.ViewModel
import me.oleg.taglibro.data.model.Note
import me.oleg.taglibro.data.repositry.NoteRepository
import me.oleg.taglibro.utitlies.getCurrentDateTime
import javax.inject.Inject

class NoteDetailViewModel
@Inject constructor(
    private val noteRepository: NoteRepository,
    private val noteId: Int
) :
    ViewModel() {

    val note = noteRepository.getNote(noteId)


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
