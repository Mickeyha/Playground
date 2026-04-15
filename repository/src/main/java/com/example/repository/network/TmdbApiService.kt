package com.example.repository.network

import com.example.repository.network.model.MovieDetail
import com.example.repository.network.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int = 1): Single<MovieResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int): Single<MovieDetail>
}
