package com.example.playground.di

import com.example.playground.feature.application.MainApplication
import com.example.playground.feature.detail.DetailFragment
import com.example.playground.feature.favorite.FavoriteFragment
import com.example.playground.feature.home.HomeFragment
import com.example.playground.feature.search.SearchFragment
import com.example.repository.di.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(application: MainApplication)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: DetailFragment)
    fun inject(fragment: FavoriteFragment)
}
