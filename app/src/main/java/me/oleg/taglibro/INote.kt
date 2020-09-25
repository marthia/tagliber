package me.oleg.taglibro

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface INote<E: Any> {

    fun get(id: String): LiveData<List<E>>

    fun getAll(): Flow<PagingData<E>>

    fun save(element: E)

    fun delete(element: E)

    fun delete(elements: Collection<E>)

    fun edit(element: E)

    fun saveFromUri(uri: Uri)

}