package com.wipro.movie.feature.movie.di

import com.wipro.movie.core.database.MainDatabase
import com.wipro.movie.core.database.dao.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MovieDatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDao(database: MainDatabase): MoviesDao = database.moviesDao()
}
