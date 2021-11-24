package com.utaputranto.capstone.ui.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.utaputranto.core.domain.model.MovieTv
import com.utaputranto.core.domain.usecase.MovieTvUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TvViewModel @Inject constructor(private val useCase: MovieTvUseCase?) : ViewModel() {

    val tvShow = useCase?.getTvShows()?.asLiveData()

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    val search = queryChannel.asFlow()
        .debounce(1000)
        .distinctUntilChanged()
        .filter { it.trim().isNotEmpty() }
        .mapLatest { useCase?.searchTvShows(it) }
        .asLiveData()

    fun isFavorite(movieTv: MovieTv) = useCase?.isFavorite(movieTv)?.asLiveData()
    fun setFavorite(movieTv: MovieTv, state: Boolean) = useCase?.setFavoriteMovieTv(movieTv, state)
}