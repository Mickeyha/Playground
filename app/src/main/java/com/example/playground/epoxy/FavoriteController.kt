package com.example.playground.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.example.repository.local.FavoriteEntity

class FavoriteController(
    private val onMovieClick: (Int) -> Unit,
    private val onRemoveClick: (Int) -> Unit
) : TypedEpoxyController<List<FavoriteEntity>>() {

    override fun buildModels(favorites: List<FavoriteEntity>?) {
        favorites?.forEach { fav ->
            favoriteCard {
                id(fav.id)
                favorite(fav)
                clickListener { _ -> onMovieClick(fav.id) }
                removeClickListener { _ -> onRemoveClick(fav.id) }
            }
        }
    }
}
