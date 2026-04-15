package com.example.playground.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.playground.R
import com.example.playground.libs.KotlinEpoxyHolder
import com.example.playground.databinding.ItemMovieBinding
import com.example.repository.network.model.Movie

@EpoxyModelClass(layout = R.layout.item_movie)
abstract class MovieCardModel : EpoxyModelWithHolder<MovieCardModel.Holder>() {

    @EpoxyAttribute lateinit var movie: Movie
    @EpoxyAttribute lateinit var clickListener: View.OnClickListener

    override fun bind(holder: Holder) {
        val b = holder.binding
        b.titleText.text = movie.title
        b.ratingText.text = String.format("%.1f", movie.voteAverage)
        Glide.with(b.posterImage.context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .placeholder(R.drawable.ic_twotone_error_outline_24px)
            .into(b.posterImage)
        b.root.setOnClickListener(clickListener)
    }

    inner class Holder : KotlinEpoxyHolder() {
        val binding by bind<ItemMovieBinding>(ItemMovieBinding::bind)
    }
}
