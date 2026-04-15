package com.example.playground.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playground.databinding.FragmentSearchBinding
import com.example.playground.di.ViewModelFactory
import com.example.playground.epoxy.MovieController
import com.example.playground.feature.application.MainApplication
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var controller: MovieController
    private val disposables = CompositeDisposable()

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller = MovieController { movieId ->
            findNavController().navigate(SearchFragmentDirections.actionSearchToDetail(movieId))
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setController(controller)
        }

        disposables.add(
            binding.searchEditText.textChanges()
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query -> viewModel.search(query.toString()) }
        )

        viewModel.results.observe(viewLifecycleOwner) { controller.setMovies(it) }
        viewModel.loading.observe(viewLifecycleOwner) { binding.progressBar.isVisible = it }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        _binding = null
    }
}
