package me.oleg.taglibro.framework.datasource.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import me.oleg.taglibro.business.domain.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM t_Notes ORDER BY id DESC")
    fun getNotes(): PagingSource<Int, Note>

    @Query("SELECT * FROM t_Notes WHERE id = :id")
    fun getNoteById(id: Long): LiveData<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(noteViewModels: List<Note>)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note : Note) : Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Delete
    suspend fun deleteNotes(list: Collection<Note>)

    @Query("SELECT * FROM t_Notes WHERE content LIKE :keyword ORDER BY id COLLATE NOCASE ASC")
    fun find(keyword: String): LiveData<List<Note>>


}
