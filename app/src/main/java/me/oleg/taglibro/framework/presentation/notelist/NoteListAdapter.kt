package me.oleg.taglibro.framework.presentation.notelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import me.oleg.taglibro.business.domain.model.Note
import me.oleg.taglibro.databinding.ListNoteItemBinding

class NoteListAdapter : PagingDataAdapter<Note, NoteListAdapter.ViewHolder>(
    Note.NoteDiffCallback
) {
    var tracker: SelectionTracker<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListNoteItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

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

    private fun createOnClickListener(note: Note?): View.OnClickListener {
        return View.OnClickListener {
            if (note != null) {
//                val extraInfoForSharedElement = FragmentNavigatorExtras(
//                    //2
//                    it to note.noteId.toString()
//                )
                it.findNavController().navigate(
                    NoteListFragmentDirections.actionNoteListFragToDetail(
                        note.noteId.toString()
                    )
                )
            }

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

        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =

            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long = itemId
            }

    }

}