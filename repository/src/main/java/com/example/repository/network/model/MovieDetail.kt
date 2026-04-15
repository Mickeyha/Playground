package com.example.repository.network.model

import com.google.gson.annotations.SerializedName

data class Genre(val id: Int, val name: String)

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("release_date") val releaseDate: String?,
    val runtime: Int?,
    val genres: List<Genre> = emptyList()
)
