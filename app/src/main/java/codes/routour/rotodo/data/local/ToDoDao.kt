package codes.routour.rotodo.data.local

import androidx.room.*
import codes.routour.rotodo.model.ToDo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDo")
    fun getAll(): List<ToDo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: ToDo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: ToDo)

    @Delete
    fun delete(todo: ToDo)

    @Query("DELETE FROM todo")
    fun deleteAll()
}