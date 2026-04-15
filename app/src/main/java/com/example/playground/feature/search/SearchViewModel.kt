package com.example.playground.feature.search

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

class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _results = MutableLiveData<List<Movie>>()
    val results: LiveData<List<Movie>> = _results

    private val _isLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _isLoading

    fun search(query: String) {
        if (query.isBlank()) {
            _results.value = emptyList()
            return
        }
        _isLoading.value = true
        disposables.add(
            repository.searchMovies(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies ->
                    _results.value = movies
                    _isLoading.value = false
                }, { error ->
                    Timber.e(error)
                    _results.value = emptyList()
                    _isLoading.value = false
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
