package me.oleg.tagliber.data

import android.util.Log
import me.oleg.tagliber.utitlies.runOnIoThread

class NoteRepository private constructor(
    private val noteDao: NoteDao
) {

    fun getNotes() = noteDao.getNotes()

    fun getNote(noteId: Int) = noteDao.getNoteById(noteId)

    fun insertNote(note: Note) {
        runOnIoThread {
            val a = noteDao.insertNote(note)
            Log.i("insertNote", a.toString())
        }
    }

    fun updateNote(note: Note) {
        runOnIoThread {
            noteDao.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        runOnIoThread {
            val a = noteDao.deleteNote(note)
            Log.i("deleteNote", a.toString())
        }
    }

    fun deleteNotes(notes : ArrayList<Note>) {
        runOnIoThread {
            val a = noteDao.deleteNotes(notes)
            Log.i("deleteNotes", a.toString())
        }
    }

    fun find(keyword : String) = noteDao.find(keyword)

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: NoteRepository? = null

        fun getInstance(noteDao: NoteDao) =
            instance ?: synchronized(this) {
                instance
                    ?: NoteRepository(noteDao).also { instance = it }
            }
    }
}