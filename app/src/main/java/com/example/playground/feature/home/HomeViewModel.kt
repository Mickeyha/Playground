package com.example.playground.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repository.MovieRepository
import com.example.repository.network.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    private var currentPage = 1
    private var isLoading = false

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val movieList = mutableListOf<Movie>()

    init {
        loadPopularMovies()
    }

    fun loadPopularMovies() {
        if (isLoading) return
        isLoading = true
        _isLoading.value = true

        disposables.add(
            repository.getPopularMovies(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    movieList.addAll(movies)
                    _movies.value = movieList.toList()
                    currentPage++
                    isLoading = false
                    _isLoading.value = false
                }, { error ->
                    Timber.e(error)
                    _error.value = error.message
                    isLoading = false
                    _isLoading.value = false
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
