package com.example.playground.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repository.MovieRepository
import com.example.repository.network.model.MovieDetail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _movie = MutableLiveData<MovieDetail>()
    val movie: LiveData<MovieDetail> = _movie

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _isLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _isLoading

    fun loadMovie(movieId: Int) {
        _isLoading.value = true
        disposables.add(
            repository.getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ detail ->
                    _movie.value = detail
                    _isLoading.value = false
                    checkFavorite(movieId)
                }, { error ->
                    Timber.e(error)
                    _isLoading.value = false
                })
        )
    }

    private fun checkFavorite(movieId: Int) {
        disposables.add(
            repository.isFavorite(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ fav -> _isFavorite.value = fav }, Timber::e)
        )
    }

    fun toggleFavorite() {
        val detail = _movie.value ?: return
        val currentFav = _isFavorite.value ?: false
        val action = if (currentFav) repository.removeFavorite(detail.id)
                     else repository.addFavorite(detail)
        disposables.add(
            action.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _isFavorite.value = !currentFav }, Timber::e)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
