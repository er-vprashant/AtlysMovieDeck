package com.prashant.atlysmoviedeck.di

import com.prashant.atlysmoviedeck.data.repository.MovieRepository
import com.prashant.atlysmoviedeck.data.repository.OfflineFirstMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: OfflineFirstMovieRepository
    ): MovieRepository
}
