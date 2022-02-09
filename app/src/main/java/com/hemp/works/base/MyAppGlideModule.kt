package com.hemp.works.base

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit


@GlideModule
@Excludes(OkHttpLibraryGlideModule::class)
class MyAppGlideModule : AppGlideModule() {
    // leave empty for now
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Glide default Bitmap Format is set to RGB_565 since it
        // consumed just 50% memory footprint compared to ARGB_8888.
        // Increase memory usage for quality with:
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888))
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(okHttpClient)
        )
    }
    companion object {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()
//        Glide.with(context)
//        .load("http://via.placeholder.com/300.png")
//        .placeholder(R.drawable.placeholder)
//        .error(R.drawable.imagenotfound)
//        .apply(requestOptions)
//        .into(ivImg);
    }
}