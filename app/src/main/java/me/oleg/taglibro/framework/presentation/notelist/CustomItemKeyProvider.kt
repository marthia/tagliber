package me.oleg.taglibro.framework.presentation.notelist

import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView

class CustomItemKeyProvider(
    private val recyclerView: RecyclerView

) : ItemKeyProvider<Long>(SCOPE_MAPPED) {

    override fun getKey(position: Int): Long? {

        return recyclerView.adapter?.getItemId(position)
    }

    override fun getPosition(key: Long): Int {

        val viewHolder = recyclerView.findViewHolderForItemId(key)

        return viewHolder?.layoutPosition ?: RecyclerView.NO_POSITION
    }
}