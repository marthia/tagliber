package me.oleg.taglibro.business.interactors

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.oleg.taglibro.business.domain.model.Note
import me.oleg.taglibro.framework.datasource.database.NoteDao

class NoteRepository
constructor(
    private val noteDao: NoteDao
) {
    fun getNotes() = noteDao.getNotes()

    fun getNote(noteId: Long) = noteDao.getNoteById(noteId)

    suspend fun insertNote(note: Note) : Long{
        return noteDao.insertNote(note)
    }

    fun updateNote(scope: CoroutineScope, note: Note) {
        scope.launch { noteDao.updateNote(note) }
    }

    fun deleteNotes(scope: CoroutineScope, notes: Collection<Note>? = null, note: Note? = null) {
        scope.launch {
            notes?.let { noteDao.deleteNotes(it) } ?: noteDao.deleteNote(note!!)
        }
    }

    fun find(keyword: String) = noteDao.find(keyword)

}