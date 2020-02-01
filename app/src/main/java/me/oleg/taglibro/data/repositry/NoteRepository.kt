package me.oleg.taglibro.data.repositry

import android.util.Log
import me.oleg.taglibro.data.model.Note
import me.oleg.taglibro.utitlies.runOnIoThread
import javax.inject.Inject

class NoteRepository @Inject constructor(
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

//    fun getNotesJson(){
//        runOnIoThread {
//            noteDao.getNotesInJson()
//                .observeOn(Schedulers.io())
//                .subscribe(onSucess -> {
//
//        })
//        }
//    }

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