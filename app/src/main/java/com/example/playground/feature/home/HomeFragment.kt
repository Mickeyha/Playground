package com.example.playground.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playground.databinding.FragmentHomeBinding
import com.example.playground.di.ViewModelFactory
import com.example.playground.epoxy.MovieController
import com.example.playground.feature.application.MainApplication
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var controller: MovieController

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = MovieController { movieId ->
            findNavController().navigate(HomeFragmentDirections.actionHomeToDetail(movieId))
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setController(controller)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as GridLayoutManager
                    if (lm.findLastVisibleItemPosition() >= lm.itemCount - 4) {
                        viewModel.loadPopularMovies()
                    }
                }
            })
        }

        viewModel.movies.observe(viewLifecycleOwner) { controller.setMovies(it) }
        viewModel.loading.observe(viewLifecycleOwner) { binding.progressBar.isVisible = it && controller.currentData.isNullOrEmpty() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
