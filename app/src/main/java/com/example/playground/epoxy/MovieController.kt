package com.example.playground.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.example.repository.network.model.Movie

class MovieController(
    private val onMovieClick: (Int) -> Unit
) : TypedEpoxyController<List<Movie>>() {

    override fun buildModels(movies: List<Movie>?) {
        movies?.forEach { movie ->
            movieCard {
                id(movie.id)
                movie(movie)
                clickListener { _ -> onMovieClick(movie.id) }
            }
        }
    }
}
