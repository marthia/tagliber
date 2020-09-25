package me.oleg.taglibro.business.domain.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "t_Notes")
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val noteId: Long = 0,
    var title: String = "Title",
    var content: String = "",
    var timeStamp: String = ""

) : Serializable {

    object NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }


    override fun toString() = content
}