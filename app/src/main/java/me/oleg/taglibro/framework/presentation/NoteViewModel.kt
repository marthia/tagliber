package me.oleg.taglibro.framework.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.oleg.taglibro.INote
import me.oleg.taglibro.business.domain.model.Note
import me.oleg.taglibro.business.interactors.NotePagingSource
import me.oleg.taglibro.business.interactors.NoteRepository
import me.oleg.taglibro.framework.datasource.database.NoteDao
import me.oleg.taglibro.utitlies.HTMLParserImpl
import java.io.File
import java.nio.charset.Charset
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
@Inject constructor(
    private var noteRepository: NoteRepository,
    private val dao: NoteDao,
    @ApplicationContext private val application: Context
) : ViewModel(), INote<Note> {


    private fun extractData(uri: Uri): String? {

        Log.i("ActivityResult0", "Uri: $uri")
        val inputStream = application.contentResolver?.openInputStream(uri)


        val htmlText = inputStream?.bufferedReader().use { it?.readText() }

//        Log.i("ActivityResult1", htmlText)
//
        val temp = htmlText
            ?.replace(
                "<style.*>.*</style>".toRegex(RegexOption.DOT_MATCHES_ALL),
                ""
            )
        val htmlWithoutBreaks = temp?.replace("<br>|</br>".toRegex(), "\n")

//        Log.i("ActivityResult2", htmlWithoutBreaks)

        inputStream?.close()

        return htmlWithoutBreaks
    }

    override fun get(id: String): LiveData<List<Note>> {
        return noteRepository.find("%$id%")
    }

    override fun getAll(): Flow<PagingData<Note>> {
        return Pager(
            PagingConfig(
                pageSize = 15,
                prefetchDistance = 5,
                enablePlaceholders = true
            )
        )
        {
            NotePagingSource(dao)
        }.flow.cachedIn(viewModelScope)
    }


    override fun save(element: Note) {
        TODO("Not yet implemented")
    }

    override fun delete(element: Note) {
        viewModelScope.launch {
            noteRepository.deleteNotes(
                note = element
            )
        }
    }

    override fun edit(element: Note) {
        TODO("Not yet implemented")
    }

    override fun saveFromUri(uri: Uri) {
        viewModelScope.launch {
            val htmlWithoutBrTags = extractData(uri)

            val file = withContext(Dispatchers.IO) {
                File.createTempFile("temp", ".html", application.cacheDir)
            }
            htmlWithoutBrTags?.let { file.writeText(it, Charset.forName("UTF8")) }


            val parser = HTMLParserImpl(file, "div")
            val title = parser.execute("title")
            val content = parser.execute("content")
            val timeStamp = parser.execute("heading")

            val note = Note(0, title, content, timeStamp)
            noteRepository.insertNote(note)
        }
    }

    override fun delete(elements: Collection<Note>) {
        viewModelScope.launch {
            noteRepository.deleteNotes(notes = elements)
        }
    }

}