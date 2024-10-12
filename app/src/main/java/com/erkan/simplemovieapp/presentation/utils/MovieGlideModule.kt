package com.erkan.simplemovieapp.presentation.utils

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class MovieGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.apply {
            setDiskCache(
                DiskLruCacheFactory(
                    "${context.cacheDir}/image_cache",
                    100 * 1024 * 1024
                )
            )

            setMemoryCache(
                LruResourceCache(
                    30 * 1024 * 1024
                )
            )

            setDefaultRequestOptions(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .format(DecodeFormat.PREFER_RGB_565) // Уменьшает использование памяти
                    .timeout(10000)
            )
        }
    }

    override fun isManifestParsingEnabled(): Boolean = false
}