package me.oleg.taglibro.business.interactors

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.oleg.taglibro.business.domain.model.Note
import me.oleg.taglibro.framework.datasource.database.NoteDao

class NotePagingSource(
    private val dao: NoteDao
) : PagingSource<Int, Note>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Note> {
        val page = params.key ?: 0

        return try {
            val entities = dao.getNotes(params.loadSize, page * params.loadSize)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Note>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}