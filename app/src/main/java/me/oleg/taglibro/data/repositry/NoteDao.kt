package me.oleg.taglibro.data.repositry

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import io.reactivex.Single
import me.oleg.taglibro.data.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM t_Notes ORDER BY id DESC")
    fun getNotes(): DataSource.Factory<Int, Note>

    @Query("SELECT * FROM t_Notes ORDER BY id DESC")
    fun getNotesInJson() : Single<Note>

    @Query("SELECT * FROM t_Notes WHERE id = :id")
    fun getNoteById(id: Int): LiveData<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(noteViewModels: List<Note>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note : Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Delete
    fun deleteNotes(list: ArrayList<Note>)

    @Query("SELECT * FROM t_Notes WHERE content LIKE :keyword ORDER BY id COLLATE NOCASE ASC")
    fun find(keyword: String): LiveData<List<Note>>


}
