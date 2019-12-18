package me.oleg.taglibro.utitlies

import android.app.Application
import android.content.Context
import me.oleg.taglibro.data.AppDatabase
import me.oleg.taglibro.data.NoteRepository
import me.oleg.taglibro.viewmodels.NoteDetailViewModelFactory
import me.oleg.taglibro.viewmodels.NoteListViewModelFactory
import me.oleg.taglibro.viewmodels.SearchViewModelFactory

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

    fun provideNoteDetailRepository(
        context: Context
    ): NoteDetailViewModelFactory {
        val repository =
            getNoteRepository(context)
        return NoteDetailViewModelFactory(repository)
    }

    fun provideSearchRepository(
        context: Context
    ): SearchViewModelFactory {
        val repository = getNoteRepository(context)
        return SearchViewModelFactory(repository)
    }

}