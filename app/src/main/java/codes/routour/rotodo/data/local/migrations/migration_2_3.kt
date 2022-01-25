package codes.routour.rotodo.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo "
                + " ADD COLUMN completed BOOLEAN NOT NULL DEFAULT 0")
    }
}