package com.maden.noteapp.domain.use_case

import android.graphics.Bitmap
import com.maden.noteapp.data.remote.DownloadImage

object ImageUseCase {
    private val downloadImage = DownloadImage()

    fun downloadImageWithUrl(
        url: String,
        exception: (exception: Exception) -> Unit = { },
        headers: Map<String, String>? = null,
        bitmap: (bitmap: Bitmap?) -> Unit
    ) {
        downloadImage.withUrl(
            url = url,
            headers = headers,
            exception = exception,
            bitmap = bitmap
        )
    }
}