package codes.routour.rotodo.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("alter table todo add column completed BOOLEAN NOT NULL DEFAULT 0;")
    }
}