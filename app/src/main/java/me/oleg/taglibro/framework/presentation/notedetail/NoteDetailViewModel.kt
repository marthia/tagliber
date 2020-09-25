package me.oleg.taglibro.framework.presentation.notedetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import me.oleg.taglibro.business.domain.model.Note
import me.oleg.taglibro.business.interactors.NoteRepository
import me.oleg.taglibro.utitlies.getCurrentDateTime

class NoteDetailViewModel
@ViewModelInject constructor(
    private val noteRepository: NoteRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
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


}
