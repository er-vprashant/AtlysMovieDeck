package com.prashant.atlysmoviedeck.di

import android.content.Context
import androidx.room.Room
import com.prashant.atlysmoviedeck.data.local.AppDatabase
import com.prashant.atlysmoviedeck.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "atlys_movie_deck.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }
}
