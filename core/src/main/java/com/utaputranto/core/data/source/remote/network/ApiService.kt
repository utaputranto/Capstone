package com.utaputranto.core.data.source.remote.network

import com.utaputranto.core.data.source.remote.response.ListMovieTvResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovies(): ListMovieTvResponse

    @GET("tv/popular")
    suspend fun getTvShows(): ListMovieTvResponse

    @GET("search/movie")
    suspend fun getSearchMovies(@Query("query") query: String?
    ): ListMovieTvResponse

    @GET("search/tv")
    suspend fun getSearchTvShows(@Query("query") query: String?
    ): ListMovieTvResponse
}
