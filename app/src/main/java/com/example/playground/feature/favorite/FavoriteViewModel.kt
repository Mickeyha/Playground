package com.example.playground.feature.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repository.MovieRepository
import com.example.repository.local.FavoriteEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _favorites = MutableLiveData<List<FavoriteEntity>>()
    val favorites: LiveData<List<FavoriteEntity>> = _favorites

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        disposables.add(
            repository.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ _favorites.value = it }, Timber::e)
        )
    }

    fun removeFavorite(movieId: Int) {
        disposables.add(
            repository.removeFavorite(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, Timber::e)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
