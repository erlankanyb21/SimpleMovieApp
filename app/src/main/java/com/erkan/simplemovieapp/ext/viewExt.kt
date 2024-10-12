package com.erkan.simplemovieapp.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.erkan.simplemovieapp.R
import com.erkan.simplemovieapp.common.Constants

fun ImageView.loadImageWithGlide(url: String?) =
    Glide.with(this)
        .load(url.toFullImageUrl())
        .centerCrop()
        .placeholder(R.drawable.placeholder_movie)
        .error(R.drawable.ic_error)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun String?.toFullImageUrl(size: String = Constants.IMAGE_SIZE_W500): String? {
    return if (this != null) {
        "${Constants.IMAGE_BASE_URL}$size$this"
    } else null
}