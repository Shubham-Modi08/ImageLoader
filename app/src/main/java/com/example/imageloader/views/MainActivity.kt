package com.example.imageloader.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.imageloader.R
import com.example.imageloader.adapter.ImageAdapter
import com.example.imageloader.databinding.ActivityMainBinding
import com.example.imageloader.viewModel.ImageViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ImageViewModel
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ImageAdapter(this, R.drawable.placeholder)
        binding.imageRecyclerView.adapter = adapter

        if (isNetworkAvailable()) {
            viewModel = ViewModelProvider(this)[ImageViewModel::class.java]
            viewModel.fetchImages()
            viewModel.images.observe(this) { images ->
                adapter.updateImages(images)
            }
        } else {
            Toast.makeText(this, "No network connectivity", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
