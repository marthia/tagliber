package me.oleg.tagliber.utitlies

import android.app.Application
import android.content.Context
import me.oleg.tagliber.data.AppDatabase
import me.oleg.tagliber.data.NoteRepository
import me.oleg.tagliber.viewmodels.NoteListViewModelFactory
import me.oleg.tagliber.viewmodels.SearchViewModelFactory

object InjectorUtils {

    private fun getNoteRepository(context: Context): NoteRepository {
        return NoteRepository.getInstance(
            AppDatabase.getInstance(
                context
            ).noteDao()
        )
    }

    fun provideNoteRepository(
        context: Application
    ): NoteListViewModelFactory {
        val repository =
            getNoteRepository(context)
        return NoteListViewModelFactory(repository, context)
    }

    fun provideSearchRepository(
        context: Context
    ): SearchViewModelFactory {
        val repository = getNoteRepository(context)
        return SearchViewModelFactory(repository)
    }


}