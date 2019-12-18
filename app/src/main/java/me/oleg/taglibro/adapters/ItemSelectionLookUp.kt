package me.oleg.taglibro.adapters

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class ItemSelectionLookUp(
    private val recycler: RecyclerView

) : ItemDetailsLookup<Long>() {


    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recycler.findChildViewUnder(e.x, e.y)
        if (view != null) {
            return (recycler.getChildViewHolder(view) as NoteListAdapter.ViewHolder)
                .getItemDetails()

        }
        return null
    }

}
