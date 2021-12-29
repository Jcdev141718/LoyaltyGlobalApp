package com.loyaltyglobal.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
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


@BindingAdapter("setCircleImage")
fun setCircleImage(imageView: AppCompatImageView,url : String?){
    url?.let {
        imageView.setImage(url,true)
    }
}
@BindingAdapter("android:countryFlag")
fun setCountryFlag(txtFlag: AppCompatTextView, url: String) {
    if (url.isNotEmpty()) {
        txtFlag.text = getCountryFlag(url.uppercase())
    }
}