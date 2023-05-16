package com.example.myweatherapp.data.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader(private val context: Context) : ImageService {
    override suspend fun loadImage(url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }
}