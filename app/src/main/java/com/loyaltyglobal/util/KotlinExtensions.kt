package com.loyaltyglobal.util

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.loyaltyglobal.R
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

fun AppCompatImageView.setImage(url: String, isRound: Boolean = false) {
    if (isRound){
        Glide.with(this.context)
            .load(url)
            .circleCrop()
            .into(this)
    }else{
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}