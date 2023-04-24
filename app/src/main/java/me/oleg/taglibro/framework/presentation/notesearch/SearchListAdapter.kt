package me.oleg.taglibro.framework.presentation.notesearch

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.oleg.taglibro.business.domain.model.Note
import me.oleg.taglibro.databinding.ListNoteItemBinding

class SearchListAdapter : ListAdapter<Note, SearchListAdapter.ViewHolder>(
    Note.NoteDiffCallback
) {

    private lateinit var query: String
    fun setQuery(query: String) {
        this.query = query
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
            itemView.tag = query
        }

    }

    private fun createOnClickListener(note: Note?): View.OnClickListener {
        return View.OnClickListener {
            if (note != null) {
                it.findNavController()
                    .navigate(SearchFragmentDirections.actionSearchToDetail(note.noteId.toString()))
            }
        }
    }

    class ViewHolder(
        private val binding: ListNoteItemBinding

    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Note) {

            // transformText(item)

            binding.apply {
                clickListener = listener
                note = item
                executePendingBindings()
            }
        }

        private fun transformText(item: Note) {
            val s = "کثیف"
            var content = item.content


            var indexOfQuery = content.indexOf(s, 0)

            if (indexOfQuery > 500) {
                val stringBuilder = StringBuilder()
                stringBuilder.append("...")

                stringBuilder.append(
                    item.content.slice(
                        300 until (indexOfQuery + s.length + 100).coerceAtMost(content.length)
                    )
                )
                stringBuilder.append("...")

                content = stringBuilder.toString()
            }

            indexOfQuery = content.indexOf(s, 0)
            val wordToSpan: Spannable = SpannableString(content)

            wordToSpan.setSpan(
                ForegroundColorSpan(Color.CYAN),
                indexOfQuery,
                indexOfQuery + s.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            binding.title.text = wordToSpan
        }

    }

}