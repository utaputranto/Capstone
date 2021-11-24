package com.utaputranto.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.utaputranto.core.domain.model.MovieTv
import java.text.SimpleDateFormat
import java.util.*

fun String?.changeDateFormat(sourceFormat: String?, newFormat: String?): String {
    return if (this.isNullOrBlank()) {
        this.toString()
    } else {
        val sourceFormatter  = SimpleDateFormat(sourceFormat, Locale.getDefault())
        val sourceDate = sourceFormatter.parse(this) ?: Date()

        val newFormatter  = SimpleDateFormat(newFormat, Locale.getDefault())
        newFormatter.format(sourceDate)
    }
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, action: (t: T) -> Unit) {
    liveData?.observe(this,
        { it?.let { t -> action(t) } }
    )
}

fun Context.showToast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun FragmentActivity.shareMovieTv(movieTv: MovieTv) {
    val mimeType = "text/plain"
    ShareCompat.IntentBuilder.from(this).apply {
        setType(mimeType)
        setChooserTitle(movieTv.title)
        setText("https://www.themoviedb.org/movie/${movieTv.id}")
        startChooser()
    }
}

inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}