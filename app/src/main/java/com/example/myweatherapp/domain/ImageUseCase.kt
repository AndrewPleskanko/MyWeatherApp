package com.example.myweatherapp.domain

import android.widget.ImageView
import com.example.myweatherapp.data.image.ImageLoader

class ImageUseCase(private val imageLoader: ImageLoader) {
    suspend fun loadImage(url: String, imageView: ImageView) {
        imageLoader.loadImage(url, imageView)
    }
}