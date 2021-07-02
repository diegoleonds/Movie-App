package com.example.moviesapi.ui.util

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviesapi.R
import com.example.moviesapi.data.api.Api

/**
 * Class to load images into views
 */
class ImgLoader(
    private val glide: RequestManager,
    private val cache: DiskCacheStrategy = DiskCacheStrategy.NONE
) {
    private val url = Api.defaultImgUrl

    fun loadImage(imgUrl: String, imgView: ImageView, imgError: Int = R.drawable.img_not_found){
        glide
            .load(url + imgUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(cache)
            .error(imgError)
            .skipMemoryCache(true)
            .into(imgView);
    }
}