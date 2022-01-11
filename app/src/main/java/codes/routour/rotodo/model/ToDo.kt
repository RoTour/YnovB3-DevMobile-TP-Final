package codes.routour.rotodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @ColumnInfo(name = "text")
    var text: String,

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)

fun ToDo.toHashMap(): HashMap<String, String> {
    return hashMapOf(
        "text" to this.text,
        "id" to this.id.toString(),
    )
}

fun Map<String, Any>.toToDo(): ToDo {
    return ToDo(this["text"] as String, this["id"] as Long)
}