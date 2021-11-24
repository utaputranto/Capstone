package com.utaputranto.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.utaputranto.core.data.Resource
import com.utaputranto.core.domain.model.MovieTv
import com.utaputranto.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieTvInteractor @Inject constructor(private val appRepository: AppRepository):
    MovieTvUseCase {

    override fun getMovies(): Flow<Resource<List<MovieTv>>> = appRepository.getMovies()

    override fun getTvShows(): Flow<Resource<List<MovieTv>>> = appRepository.getTvShows()

    override fun getFavoriteMovies(): LiveData<PagedList<MovieTv>> =
        appRepository.getFavoriteMovies()

    override fun getFavoriteTvShows(): LiveData<PagedList<MovieTv>> =
        appRepository.getFavoriteTvShows()

    override fun searchMovies(query: String): Flow<Resource<List<MovieTv>>> =
        appRepository.searchMovies(query)

    override fun searchTvShows(query: String): Flow<Resource<List<MovieTv>>> =
        appRepository.searchTvShows(query)

    override fun setFavoriteMovieTv(movieTv: MovieTv, saved: Boolean) =
        appRepository.setFavoriteMovieTv(movieTv, saved)

    override fun isFavorite(movieTv: MovieTv) = appRepository.isFavorite(movieTv)
}