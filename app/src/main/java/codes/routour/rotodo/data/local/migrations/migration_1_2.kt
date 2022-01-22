package codes.routour.rotodo.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo "
                + " ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
    }
}