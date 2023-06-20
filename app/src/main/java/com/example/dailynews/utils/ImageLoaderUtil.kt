package com.example.dailynews.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.dailynews.R

object ImageLoaderUtil {

    fun loadImages(imageView: ImageView, url : String, context : Context) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }
}