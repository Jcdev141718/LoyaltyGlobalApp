package com.loyaltyglobal.util

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import java.util.*

/**
 * Created by Abhin.
 */

fun String.isEmailValid(): Boolean = doesStringMatchPattern(this, Constants.REGEX_EMAIL)

fun String.isPasswordValid(): Boolean =
    doesStringMatchPattern(this, Constants.PASSWORD_PATTERN_WITH_ONE_SPECIAL_CHARS)

fun String.firstLetterCap() : String = this.substring(0, 1).uppercase(Locale.ROOT) + this.substring(1)
    .lowercase(
        Locale.ROOT
    )


fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun AppCompatImageView.setImage(url: Any, isRound: Boolean = false) {
    if (isRound){
        Glide.with(getParentActivity()!!)
            .load(url)
            .circleCrop()
            .into(this)
    }else{
        Glide.with(getParentActivity()!!)
            .load(url)
            .into(this)
    }
}
