package me.oleg.taglibro.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import me.oleg.taglibro.data.Note
import me.oleg.taglibro.data.NoteRepository
import me.oleg.taglibro.utitlies.CONTENT_CLASS_NAME
import me.oleg.taglibro.utitlies.HTMLParser
import me.oleg.taglibro.utitlies.TIMESTAMP_CLASS_NAME
import me.oleg.taglibro.utitlies.TITLE_CLASS_NAME
import java.io.File
import java.nio.charset.Charset

class NoteViewModel
internal constructor(
    private val noteRepository: NoteRepository,
    application: Application
) :
    AndroidViewModel(application) {
    companion object {
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5
        private const val ENABLE_PLACEHOLDERS = true
    }

    private val context: Context = getApplication<Application>().applicationContext

    val noteList = LivePagedListBuilder(
        noteRepository.getNotes(), PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
            .build()
    ).build()

    var filteredList = noteRepository.getNotes()

    fun saveNewData(uri: Uri) {

        val htmlWithoutBrTags = extractData(uri)

        val file = File.createTempFile("temp", ".html", context.cacheDir)
        htmlWithoutBrTags?.let { file.writeText(it, Charset.forName("UTF8")) }


        val parser = HTMLParser(file)
        val title = parser.getItemByClass(TITLE_CLASS_NAME)
        val content = parser.getItemByClass(CONTENT_CLASS_NAME)
        val timeStamp = parser.getItemByClass(TIMESTAMP_CLASS_NAME)

        val note = Note(0, title, content, timeStamp)
        noteRepository.insertNote(note)

    }

    fun deleteData(deleteItems: ArrayList<Note>) {
        if (deleteItems.size > 1) {
            noteRepository.deleteNotes(deleteItems)
        } else {
            noteRepository.deleteNote(deleteItems[0])
        }

    }

    private fun extractData(uri: Uri): String? {

        Log.i("ActivityResult0", "Uri: $uri")
        val inputStream = context.contentResolver?.openInputStream(uri)


        val htmlText = inputStream?.bufferedReader().use { it?.readText() }

        Log.i("ActivityResult1", htmlText)

        val temp = htmlText
            ?.replace(
                "<style.*>.*</style>".toRegex(RegexOption.DOT_MATCHES_ALL),
                ""
            )
        val htmlWithoutBreaks = temp?.replace("<br>|</br>".toRegex(), "\n")

        Log.i("ActivityResult2", htmlWithoutBreaks)

        inputStream?.close()

        return htmlWithoutBreaks
    }

}