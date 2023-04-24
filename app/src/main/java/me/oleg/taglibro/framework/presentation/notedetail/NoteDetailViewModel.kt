package me.oleg.taglibro.framework.presentation.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.oleg.taglibro.business.domain.model.Note
import me.oleg.taglibro.business.interactors.NoteRepository
import me.oleg.taglibro.utitlies.getCurrentDateTime
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel
@Inject constructor(
    private val noteRepository: NoteRepository
) :
    ViewModel() {

    private val insertedId = MutableLiveData<Long>()

    val changedId: LiveData<Long>
        get() = insertedId

    fun getNoteById(noteId: Long) =
        noteRepository.getNote(noteId)


    fun save(id: Long, title: String, text: String) {
        if (id == -1L) {
            saveNewNote(title, text)
        } else {
            saveChanges(id, title, text)
        }
    }

    private fun saveChanges(id: Long, title: String, text: String) {

        noteRepository.updateNote(
            viewModelScope,
            Note(
                noteId = id,
                title = title,
                content = text,
                timeStamp = getCurrentDateTime()
            )
        )

    }


    private fun saveNewNote(title: String, text: String) {

        viewModelScope.launch {

            insertedId.postValue(
                noteRepository.insertNote(
                    Note(
                        title = title,
                        content = text,
                        timeStamp = getCurrentDateTime()
                    )
                )
            )
        }

    }

    fun delete(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNotes(note = note)
        }
    }


}
