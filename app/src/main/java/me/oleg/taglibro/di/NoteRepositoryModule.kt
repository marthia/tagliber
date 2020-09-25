package me.oleg.taglibro.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.oleg.taglibro.business.interactors.NoteRepository
import me.oleg.taglibro.framework.datasource.database.NoteDao
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NoteRepositoryModule {

    @Singleton
   @Provides
    fun provideNoteRepository(
        noteDao: NoteDao
    ): NoteRepository {
        return NoteRepository(noteDao)
    }
}
