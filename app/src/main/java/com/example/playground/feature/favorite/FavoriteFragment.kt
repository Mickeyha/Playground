package com.example.playground.feature.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playground.databinding.FragmentFavoriteBinding
import com.example.playground.di.ViewModelFactory
import com.example.playground.epoxy.FavoriteController
import com.example.playground.feature.application.MainApplication
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var controller: FavoriteController

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = FavoriteController(
            onMovieClick = { movieId ->
                findNavController().navigate(FavoriteFragmentDirections.actionFavoriteToDetail(movieId))
            },
            onRemoveClick = { movieId -> viewModel.removeFavorite(movieId) }
        )
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setController(controller)
        }

        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            controller.setFavorites(favorites)
            binding.emptyText.isVisible = favorites.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
