package com.example.imageloader.network

import com.example.imageloader.data.ImageItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("content/misc/media-coverages")
    suspend fun getImageList(@Query("limit") limit: Int = 100): List<ImageItem>
}


