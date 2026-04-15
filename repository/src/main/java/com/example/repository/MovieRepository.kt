package com.example.repository

import com.example.repository.local.FavoriteDao
import com.example.repository.local.FavoriteEntity
import com.example.repository.network.TmdbApiService
import com.example.repository.network.model.Movie
import com.example.repository.network.model.MovieDetail
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val apiService: TmdbApiService,
    private val favoriteDao: FavoriteDao
) {
    fun getPopularMovies(page: Int = 1): Single<List<Movie>> =
        apiService.getPopularMovies(page).map { it.results }

    fun searchMovies(query: String, page: Int = 1): Single<List<Movie>> =
        apiService.searchMovies(query, page).map { it.results }

    fun getMovieDetail(movieId: Int): Single<MovieDetail> =
        apiService.getMovieDetail(movieId)

    fun getFavorites(): Flowable<List<FavoriteEntity>> =
        favoriteDao.getAllFavorites()

    fun isFavorite(movieId: Int): Single<Boolean> =
        favoriteDao.isFavorite(movieId).map { it > 0 }

    fun addFavorite(movie: MovieDetail): Completable =
        favoriteDao.insertFavorite(
            FavoriteEntity(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                posterPath = movie.posterPath,
                voteAverage = movie.voteAverage,
                releaseDate = movie.releaseDate
            )
        )

    fun removeFavorite(movieId: Int): Completable =
        favoriteDao.deleteFavorite(movieId)
}
