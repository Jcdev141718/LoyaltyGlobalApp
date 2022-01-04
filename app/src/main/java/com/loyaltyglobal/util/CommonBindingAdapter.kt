package com.loyaltyglobal.util

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.loyaltyglobal.data.source.localModels.userPassResponse.Notification


/**
 * Created by Abhin.
 */

@BindingAdapter("setStoriesBackGround")
fun AppCompatImageView.setStoriesBackGround(data: Notification) {
    when (data.type) {
        Constants.NOTIFICATION_TYPE_IMAGE -> {
            data.imageUrl?.let { setImage(it) }
        }
        Constants.NOTIFICATION_TYPE_TEXT -> {
            data.backgroundColor?.startColor?.let { setBackgroundColor(Color.parseColor("#$it")) }
        }
    }
}

@BindingAdapter("setImage")
fun setImage(imageView: AppCompatImageView, url: String?) {
    url?.let {
        imageView.setImage(url)
    }
}


@BindingAdapter("setCircleImage")
fun setCircleImage(imageView: AppCompatImageView, url: String?) {
    url?.let {
        imageView.setImage(url, true)
    }
}

@BindingAdapter("android:countryFlag")
fun setCountryFlag(txtFlag: AppCompatTextView, url: String) {
    if (url.isNotEmpty()) {
        txtFlag.text = getCountryFlag(url.uppercase())
    }
}

@BindingAdapter("setNotificationImage")
fun setNotificationImage(image: AppCompatImageView, url: String?) {
    url?.let {
        image.setNotificationBlurImage(url)
    }
}

@BindingAdapter("setStoriesFullScreenBackGround")
fun AppCompatImageView.setStoriesFullScreenBackGround(data: Notification) {
    when (data.type) {
        Constants.NOTIFICATION_TYPE_IMAGE -> {
            data.imageUrl?.let { setNotificationBlurImage(it) }
        }
        Constants.NOTIFICATION_TYPE_TEXT -> {
            data.backgroundColor?.startColor?.let { setBackgroundColor(Color.parseColor("#$it")) }
        }
    }
}


@SuppressLint("CheckResult")
fun <T> ImageView.setNotificationBlurImage(
    obj: T,
    options: RequestOptions? = null,
    listener: RequestListener<Drawable>? = null,
) {
    Glide.with(this).load(obj).also {
        it.apply(options ?: RequestOptions.centerCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE))
        it.transform(GlideBlurTransformation(context))
        if (listener != null) it.listener(listener)
        it.into(this)
    }
}

@BindingAdapter("app:setTextVisibility")
fun AppCompatTextView.setTextVisibility(data: Notification) {
    if (!data.isOpenedOnce && data.type == Constants.NOTIFICATION_TYPE_TEXT) {
        show()
    } else {
        hide()
    }
}

@BindingAdapter("app:linkWithUnderline")
fun AppCompatTextView.linkWithUnderline(link: String) {
    val content = SpannableString(link)
    content.setSpan(UnderlineSpan(), 0, content.length, 0)
    text = content
}
