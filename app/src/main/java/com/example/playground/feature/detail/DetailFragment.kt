package com.example.playground.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.playground.R
import com.example.playground.databinding.FragmentDetailBinding
import com.example.playground.di.ViewModelFactory
import com.example.playground.feature.application.MainApplication
import javax.inject.Inject

class DetailFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: DetailViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadMovie(args.movieId)

        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            binding.apply {
                titleText.text = movie.title
                overviewText.text = movie.overview
                ratingText.text = String.format("%.1f", movie.voteAverage)
                releaseDateText.text = movie.releaseDate ?: ""
                genreText.text = movie.genres.joinToString(" · ") { it.name }
                runtimeText.text = movie.runtime?.let { "${it}min" } ?: ""

                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500${movie.backdropPath ?: movie.posterPath}")
                    .placeholder(R.drawable.ic_twotone_error_outline_24px)
                    .into(backdropImage)
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFav ->
            binding.fabFavorite.setImageResource(
                if (isFav) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_border
            )
        }

        viewModel.loading.observe(viewLifecycleOwner) { binding.progressBar.isVisible = it }

        binding.fabFavorite.setOnClickListener { viewModel.toggleFavorite() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
