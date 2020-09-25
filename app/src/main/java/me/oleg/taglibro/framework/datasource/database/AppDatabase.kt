package me.oleg.taglibro.framework.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.oleg.taglibro.business.domain.model.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "db_Notes"
    }
}