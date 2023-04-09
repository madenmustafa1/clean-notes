package com.maden.noteapp.presentation.util.extensions

import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.maden.noteapp.R
import com.maden.noteapp.domain.use_case.ImageUseCase

@BindingAdapter("app:editedChip")
fun editedChip(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:downloadImageWithUrl")
fun downloadImageWithUrl(view: View, url: String?) {
    val imageView = view as? ImageView ?: return
    imageView.downloadImageWithUrl(url = url)

}

fun ImageView.downloadImageWithUrl(url: String?) {
    if (url == null || url.isEmpty()) {
        setNoPicturesImage(this)
        return
    }

    ImageUseCase.downloadImageWithUrl(
        url = url,
        bitmap = { bitmap ->
            bitmap?.let {
                this.setImageBitmap(bitmap)
            } ?: run {
                setNoPicturesImage(this)
            }
        },
        exception = {
            setNoPicturesImage(this)
        }
    )
}

private fun setNoPicturesImage(imageView: ImageView) {
    imageView.setImageDrawable(
        AppCompatResources.getDrawable(
            imageView.context,
            R.drawable.no_pictures
        )
    )
}
