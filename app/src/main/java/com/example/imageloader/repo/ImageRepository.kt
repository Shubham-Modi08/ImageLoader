package com.example.imageloader.repo

import com.example.imageloader.data.ImageItem
import com.example.imageloader.network.ApiService

class ImageRepository(private val apiService: ApiService) {
    suspend fun getImageList(): List<ImageItem> {
        return apiService.getImageList()
    }
}
