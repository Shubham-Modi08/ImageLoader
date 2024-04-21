package com.example.imageloader.data

import com.google.gson.annotations.SerializedName

data class Thumbnail(
    val id: String,
    val version: Int,
    val domain: String,
    val basePath: String,
    val key: String,
    val qualities: List<Int>,
    val aspectRatio: Int
)

data class ImageItem(
    val id: String,
    val title: String,
    val language: String,
    val thumbnail: Thumbnail,
    @SerializedName("mediaType")
    val mediaType: Int,
    val coverageURL: String,
    val publishedAt: String,
    val publishedBy: String,
    val backupDetails: BackupDetails
)

data class BackupDetails(
    val pdfLink: String,
    val screenshotURL: String
)

