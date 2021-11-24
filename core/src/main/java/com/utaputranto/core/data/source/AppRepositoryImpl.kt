package com.utaputranto.core.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.utaputranto.core.data.NetworkBoundResource
import com.utaputranto.core.data.Resource
import com.utaputranto.core.data.source.local.LocalDataSource
import com.utaputranto.core.data.source.remote.RemoteDataSource
import com.utaputranto.core.data.source.remote.network.ApiResponse
import com.utaputranto.core.data.source.remote.response.MovieTvResponse
import com.utaputranto.core.domain.model.MovieTv
import com.utaputranto.core.domain.repository.AppRepository
import com.utaputranto.core.utils.AppExecutors
import com.utaputranto.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : AppRepository {
    override fun getMovies(): Flow<Resource<List<MovieTv>>> =
        object : NetworkBoundResource<List<MovieTv>, List<MovieTvResponse>>() {
            override fun loadFromDB(): Flow<List<MovieTv>> {
                return localDataSource.getMovieList().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieTv>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieTvResponse>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieTvResponse>) {
                val tourismList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovieTv(tourismList)
            }
        }.asFlow()

    override fun getTvShows(): Flow<Resource<List<MovieTv>>> =
        object : NetworkBoundResource<List<MovieTv>, List<MovieTvResponse>>() {
            override fun loadFromDB(): Flow<List<MovieTv>> {
                return localDataSource.getTvShowList().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieTv>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieTvResponse>>> =
                remoteDataSource.getTvShows()

            override suspend fun saveCallResult(data: List<MovieTvResponse>) {
                val tourismList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovieTv(tourismList)
            }
        }.asFlow()

    override fun getFavoriteMovies(): LiveData<PagedList<MovieTv>> {
        val favMovies = localDataSource.getFavoriteMovies().map {
            DataMapper.mapFavoriteEntityToDomain(it)
        }

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(favMovies, config).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<MovieTv>> {
        val favTvShows = localDataSource.getFavoriteTvShows().map {
            DataMapper.mapFavoriteEntityToDomain(it)
        }

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(favTvShows, config).build()
    }

    override fun searchMovies(query: String): Flow<Resource<List<MovieTv>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchMovies(query).first()) {
            is ApiResponse.Success -> {
                val response = DataMapper.mapResponseToEntities(apiResponse.data)
                emit(Resource.Success(DataMapper.mapEntitiesToDomain(response)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error<List<MovieTv>>("No result found, please try different keyword"))
            }
            is ApiResponse.Error -> {
                emit(
                    Resource.Error<List<MovieTv>>(
                        apiResponse.errorMessage
                    )
                )
            }
        }
    }

    override fun searchTvShows(query: String): Flow<Resource<List<MovieTv>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchTvShows(query).first()) {
            is ApiResponse.Success -> {
                val response = DataMapper.mapResponseToEntities(apiResponse.data)
                emit(Resource.Success(DataMapper.mapEntitiesToDomain(response)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error<List<MovieTv>>("No result found, please try different keyword"))
            }
            is ApiResponse.Error -> {
                emit(
                    Resource.Error<List<MovieTv>>(
                        apiResponse.errorMessage
                    )
                )
            }
        }
    }

    override fun setFavoriteMovieTv(movieTv: MovieTv, saved: Boolean) {
        val movieTvEntity = DataMapper.mapDomainToFavoriteEntity(movieTv)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovieTv(movieTvEntity, saved) }
    }

    override fun isFavorite(movieTv: MovieTv): Flow<Boolean> {
        val movieTvEntity = DataMapper.mapDomainToFavoriteEntity(movieTv)
        return flow {
            emit(localDataSource.isFavorite(movieTvEntity))
        }
    }
}