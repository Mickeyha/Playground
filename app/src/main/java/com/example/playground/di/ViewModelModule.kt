package com.example.playground.di

import androidx.lifecycle.ViewModel
import com.example.playground.feature.detail.DetailViewModel
import com.example.playground.feature.favorite.FavoriteViewModel
import com.example.playground.feature.home.HomeViewModel
import com.example.playground.feature.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds @IntoMap @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(vm: SearchViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(vm: DetailViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(vm: FavoriteViewModel): ViewModel
}
