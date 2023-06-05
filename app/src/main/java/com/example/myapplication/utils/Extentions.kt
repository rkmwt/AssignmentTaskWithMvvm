package com.example.myassignmentrahul.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide

inline val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun ImageView.loadImageFromURL(url: String?, placeholder: Int? = null) {
    try {
        if (placeholder != null) {
            Glide.with(this).load(url).placeholder(placeholder).fitCenter().into(this)
        } else {
            Glide.with(this).load(url).fitCenter()
                .into(this)
        }
    } catch (e: Exception) {

    }

}