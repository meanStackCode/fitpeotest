package com.example.myapplication

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

/**
 * Created by Deshraj Sharma on 12-03-2023.
 */

@BindingAdapter("imageUrl", "placeholder")
fun ImageView.loadImage(url: String?, placeholder: Drawable) {
    Picasso.get().load(url).placeholder(placeholder).into(this)
}

@BindingAdapter("android:visibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}