package me.oleg.tagliber.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import me.oleg.tagliber.data.Note
import me.oleg.tagliber.databinding.ListNoteItemBinding
import me.oleg.tagliber.fragment.NoteListFragmentDirections

class NoteListAdapter : PagedListAdapter<Note, NoteListAdapter.ViewHolder>(
    NoteDiffCallback()
) {
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListNoteItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = getItem(position)
        holder.apply {
            bind(createOnClickListener(note), note)
            itemView.tag = note
        }
        tracker?.let {
            holder.handleSelection(note, it.isSelected(position.toLong()))
        }

    }

    fun createOnClickListener(note: Note?): View.OnClickListener {
        return View.OnClickListener {
            if (note != null)
                it.findNavController().navigate(
                    NoteListFragmentDirections.actionNoteListFragToDetail(
                        note.noteId
                    )
                )

        }
    }

    class ViewHolder(
        private val binding: ListNoteItemBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Note?) {

            binding.apply {
                clickListener = listener
                note = item
                executePendingBindings()
            }
        }

        fun handleSelection(note: Note?, isActivated: Boolean) {
            itemView.isActivated = isActivated

            if (isActivated) {

            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }

    }

}