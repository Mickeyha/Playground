package com.example.repository.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flowable<List<FavoriteEntity>>

    @Query("SELECT COUNT(*) FROM favorites WHERE id = :movieId")
    fun isFavorite(movieId: Int): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(movie: FavoriteEntity): Completable

    @Query("DELETE FROM favorites WHERE id = :movieId")
    fun deleteFavorite(movieId: Int): Completable
}
