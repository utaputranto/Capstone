package com.utaputranto.core.domain.model

import android.os.Parcelable
import com.utaputranto.core.utils.changeDateFormat
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieTv(
    val id: Int,
    val popularity: Float?,
    val vote_average: Float?,
    val title: String?,
    val name: String?,
    val release_date: String?,
    val first_air_date: String?,
    val backdrop_path: String?,
    val overview: String?,
    val poster_path: String?
) : Parcelable {
    fun getRatingAverage() = vote_average?.div(2) ?: 0f
    fun getFormattedDate() = release_date?.changeDateFormat("yyyy-MM-dd", "dd MMM yyyy")
        ?: first_air_date.changeDateFormat("yyyy-MM-dd", "dd MMM yyyy")
}