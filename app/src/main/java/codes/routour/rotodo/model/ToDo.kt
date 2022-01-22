package codes.routour.rotodo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.HashMap

@Entity
data class ToDo(
    @ColumnInfo(name = "text")
    var text: String,

    @ColumnInfo(name = "updatedAt")
    var updatedAt: Long = System.currentTimeMillis(),

    @PrimaryKey(autoGenerate = true)
    var id: Long? = UUID.randomUUID().leastSignificantBits
)

fun ToDo.toHashMap(): HashMap<String, String> {
    return hashMapOf(
        "text" to this.text,
        "id" to this.id.toString(),
        "updatedAt" to this.updatedAt.toString()
    )
}

fun Map<String, Any>.toToDo(): ToDo {
    return ToDo(this["text"] as String, this["updatedAt"] as Long, this["id"] as Long)
}