package com.example.imageloader.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageloader.data.Thumbnail
import com.example.imageloader.network.ApiClient
import com.example.imageloader.repo.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel() {
    private val _images = MutableLiveData<List<Thumbnail>>()
    val images: LiveData<List<Thumbnail>> get() = _images

    fun fetchImages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageRepository = ImageRepository(ApiClient.apiService)
                val imageList = imageRepository.getImageList()
                Log.d("ImageViewModel", imageList.toString())
                _images.postValue(imageList.map { it.thumbnail })
            } catch (e: Exception) {
                Log.e("ImageViewModel", "Error fetching images", e)
            }
        }
    }
}



