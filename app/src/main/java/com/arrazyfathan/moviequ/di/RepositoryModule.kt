package com.arrazyfathan.moviequ.di

import com.arrazyfathan.moviequ.data.repository.MovieRepositoryImpl
import com.arrazyfathan.moviequ.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}
