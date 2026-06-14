package com.example.tandem.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The single database instance for the app.
 * Ties together all entities and DAOs.
 */
@Database(
    entities = [MemberEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likedMemberDao(): LikedMemberDao
}