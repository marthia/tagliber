package me.oleg.tagliber.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "t_Notes")
data class Note(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val noteId: Int = 0,
    val title: String = "Title",
    val content: String = "",
    val timeStamp: String = ""

) : Serializable {

    override fun toString() = content
}