package com.prashant.atlysmoviedeck.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, MovieRemoteKeyEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun movieRemoteKeyDao(): MovieRemoteKeyDao
}
