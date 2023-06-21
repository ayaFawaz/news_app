package com.example.dailynews.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dailynews.R

object ImageLoaderUtil {

    fun loadImages(imageView: ImageView, url : String, context : Context) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_placeholder)
        Glide.with(context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }
}