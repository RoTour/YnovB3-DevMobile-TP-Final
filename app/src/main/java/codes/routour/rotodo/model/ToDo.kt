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
