package me.oleg.taglibro.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.oleg.taglibro.data.model.Note
import me.oleg.taglibro.databinding.ListNoteItemBinding
import me.oleg.taglibro.ui.list.SearchFragmentDirections

class SearchListAdapter : ListAdapter<Note, SearchListAdapter.ViewHolder>(
    NoteDiffCallback()
) {

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

    }

    fun createOnClickListener(note: Note?): View.OnClickListener {
        return View.OnClickListener {
            if (note != null) {
                it.findNavController()
                    .navigate(SearchFragmentDirections.actionSearchToDetail(note.noteId))
            }
        }
    }

    class ViewHolder(
        private val binding: ListNoteItemBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Note) {

            binding.apply {
                clickListener = listener
                note = item
                executePendingBindings()
            }
        }

    }

}