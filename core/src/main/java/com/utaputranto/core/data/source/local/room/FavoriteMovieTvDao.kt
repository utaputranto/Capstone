package com.utaputranto.core.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.utaputranto.core.data.source.local.entity.FavoriteMovieTvEntity

@Dao
interface FavoriteMovieTvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: FavoriteMovieTvEntity)

    @Delete
    fun delete(data: FavoriteMovieTvEntity)

    @Query("select * from favorite_movie_tv where title != ''")
    fun getFavoriteMovies(): DataSource.Factory<Int, FavoriteMovieTvEntity>

    @Query("select * from favorite_movie_tv where name != ''")
    fun getFavoriteTvShows(): DataSource.Factory<Int, FavoriteMovieTvEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_movie_tv WHERE id = :id)")
    suspend fun isExist(id: Int): Boolean
}