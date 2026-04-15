package com.example.playground.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.playground.R
import com.example.playground.libs.KotlinEpoxyHolder
import com.example.playground.databinding.ItemFavoriteBinding
import com.example.repository.local.FavoriteEntity

@EpoxyModelClass(layout = R.layout.item_favorite)
abstract class FavoriteCardModel : EpoxyModelWithHolder<FavoriteCardModel.Holder>() {

    @EpoxyAttribute lateinit var favorite: FavoriteEntity
    @EpoxyAttribute lateinit var clickListener: View.OnClickListener
    @EpoxyAttribute lateinit var removeClickListener: View.OnClickListener

    override fun bind(holder: Holder) {
        val b = holder.binding
        b.titleText.text = favorite.title
        b.ratingText.text = String.format("%.1f", favorite.voteAverage)
        Glide.with(b.posterImage.context)
            .load("https://image.tmdb.org/t/p/w500${favorite.posterPath}")
            .placeholder(R.drawable.ic_twotone_error_outline_24px)
            .into(b.posterImage)
        b.root.setOnClickListener(clickListener)
        b.removeButton.setOnClickListener(removeClickListener)
    }

    inner class Holder : KotlinEpoxyHolder() {
        val binding by bind<ItemFavoriteBinding>(ItemFavoriteBinding::bind)
    }
}
