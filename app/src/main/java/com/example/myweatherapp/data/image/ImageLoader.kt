package com.example.myweatherapp.data.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader: ImageService {
    override suspend fun loadImage(url: String, imageView: ImageView) {
        Glide.with(imageView)
            .load(url)
            .into(imageView)
    }
}