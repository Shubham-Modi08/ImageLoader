package com.example.imageloader.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


        viewModel = ViewModelProvider(this)[ImageViewModel::class.java]
        viewModel.fetchImages()
        viewModel.images.observe(this) { images ->
            adapter.updateImages(images)
        }


    }
}