package com.example.myweatherapp.data.image

import android.widget.ImageView

interface ImageService {
    suspend fun loadImage(url: String, imageView: ImageView)
}