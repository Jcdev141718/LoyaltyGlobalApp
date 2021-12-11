package com.loyaltyglobal.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter

/**
 * Created by Abhin.
 */

@BindingAdapter("setImage")
fun setImage(imageView: AppCompatImageView,url : String?){
    url?.let {
        imageView.setImage(url)
    }
}