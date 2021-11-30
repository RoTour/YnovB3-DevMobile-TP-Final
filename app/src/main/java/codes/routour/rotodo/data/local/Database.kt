package codes.routour.rotodo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import codes.routour.rotodo.model.ToDo

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}

object Database {
    private var db: ToDoDatabase? = null

    fun getDb(context: Context): ToDoDatabase {
        if (db == null) {
            db = Room
                .databaseBuilder(
                    context, ToDoDatabase::class.java, "tp-sqlite"
                ).build()
        }
        return db!!
    }
}