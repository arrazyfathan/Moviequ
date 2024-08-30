package com.arrazyfathan.moviequ.data.mapper

import com.arrazyfathan.moviequ.data.local.entity.MovieEntity
import com.arrazyfathan.moviequ.data.remote.dto.MovieDto
import com.arrazyfathan.moviequ.domain.Movie

fun MovieDto.toMovieEntity(): MovieEntity {
    return MovieEntity(
        imdbID = imdbID,
        poster = poster,
        title = title,
        type = type,
        year = year,
    )
}

fun MovieDto.toDomain(): Movie {
    return Movie(
        imdbID = imdbID,
        poster = poster,
        title = title,
        type = type,
        year = year,
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        imdbID = imdbID,
        poster = poster,
        title = title,
        type = type,
        year = year
    )
}
