package me.oleg.tagliber.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "t_Notes")
data class Note(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val noteId: Int = 0,
    var title: String = "Title",
    var content: String = "",
    var timeStamp: String = ""

) : Serializable {

    /*var titleUi: String
    @Bindable
    get() {
        if (title.isNotEmpty()) return title.trim()
        return ""
    }
    set(value) {
        title = value
    }

    var contentUi: String
    @Bindable
    get() {
        if (content.isNotEmpty()) return content
        return ""
    }
    set(value) {
        content = value
    }*/

    override fun toString() = content
}