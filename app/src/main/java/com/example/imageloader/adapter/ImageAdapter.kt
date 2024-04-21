package com.example.imageloader.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imageloader.utlitiy.ImageLoaderUtils
import com.example.imageloader.data.Thumbnail

import com.example.imageloader.databinding.ItemImageBinding

class ImageAdapter(context: Context, private val placeholder: Int) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val imageList = mutableListOf<Thumbnail>()

    init {
        ImageLoaderUtils.initialize(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = imageList[position]
        val imageUrl = "${item.domain}/${item.basePath}/0/${item.key}"
        ImageLoaderUtils.loadImage(imageUrl, holder.binding.imageView, placeholder)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateImages(images: List<Thumbnail>) {
        imageList.clear()
        imageList.addAll(images)
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
}

