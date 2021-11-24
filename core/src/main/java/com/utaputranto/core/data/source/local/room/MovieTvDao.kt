package com.utaputranto.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.utaputranto.core.data.source.local.entity.MovieTvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTvDao {
    @Query("select * from movie_tv where title != ''")
    fun getMovies(): Flow<List<MovieTvEntity>>

    @Query("select * from movie_tv where name != ''")
    fun getTvShows(): Flow<List<MovieTvEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieTv(movie: List<MovieTvEntity>)
}