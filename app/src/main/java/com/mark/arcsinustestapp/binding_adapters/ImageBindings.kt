package com.mark.arcsinustestapp.binding_adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:src", "android:placeholder", requireAll = false)
fun setImageFromUrl(view: ImageView, srcUrl: String?, placeholder: Drawable?){
    srcUrl?.let { url ->
        Glide.with(view).load(url).apply {
            if(placeholder != null)
                placeholder(placeholder)
        }.into(view)
    }
}