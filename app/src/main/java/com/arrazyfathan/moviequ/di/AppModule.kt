package com.arrazyfathan.moviequ.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.arrazyfathan.moviequ.BuildConfig
import com.arrazyfathan.moviequ.data.local.dao.MovieDao
import com.arrazyfathan.moviequ.data.local.dao.RemoteKeyDao
import com.arrazyfathan.moviequ.data.local.database.MovieDatabase
import com.arrazyfathan.moviequ.data.remote.api.OmdbApi
import com.arrazyfathan.moviequ.utils.CustomHttpLogger
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor(CustomHttpLogger()).apply {
            level =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
        }
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "movie.db").build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()

    @Provides
    @Singleton
    fun provideMovieApi(gson: Gson, okHttpClient: OkHttpClient): OmdbApi {
        return Retrofit.Builder()
            .baseUrl(OmdbApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(OmdbApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesDao(moviesDatabase: MovieDatabase): MovieDao = moviesDatabase.getMoviesDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(moviesDatabase: MovieDatabase): RemoteKeyDao =
        moviesDatabase.getRemoteKeysDao()
}
