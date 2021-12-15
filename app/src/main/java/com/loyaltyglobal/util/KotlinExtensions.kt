package com.loyaltyglobal.util

import android.app.Activity
import android.content.ContextWrapper
import android.text.InputFilter
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
    if (isRound) {
        Glide.with(getParentActivity()!!)
            .load(url)
            .circleCrop()
            .into(this)
    } else {
        Glide.with(getParentActivity()!!)
            .load(url)
            .into(this)
    }
}

@BindingAdapter("setNotificationBg")
fun ConstraintLayout.setNotificationBg(isNotificationRead: Boolean = false) {
    if (isNotificationRead) {
        val value = TypedValue()
        context.theme.resolveAttribute(com.loyaltyglobal.R.attr.main_card, value, true)
        this.setBackgroundColor(value.data)
    } else {
        val value = TypedValue()
        context.theme.resolveAttribute(com.loyaltyglobal.R.attr.black_white, value, true)
        this.setBackgroundColor(value.data)
    }
}

fun getCountryFlag(flagCode: String): String {
    val flagOffset = 0x1F1E6
    val asciiOffset = 0x41
    val firstChar = Character.codePointAt(flagCode, 0) - asciiOffset + flagOffset
    val secondChar = Character.codePointAt(flagCode, 1) - asciiOffset + flagOffset
    return (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
}
fun Activity.showToast(message: String){
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun View.setFullHeight() {
    val layoutParams = this.layoutParams
    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
    this.layoutParams = layoutParams
}

fun Fragment.openBottomSheet(bottomSheet : BottomSheetDialogFragment) {
    bottomSheet.show(
        this.requireActivity().supportFragmentManager,
        bottomSheet::class.java.simpleName
    )
}

fun EditText.preventSpecialCharacter() {
    val blockCharacterSet = "!#\$%&(){|}~:;<=>?@*+,./^_`-\\'\\\" \\t\\r\\n\\f]+ "
    val filterSpecialChar =
        InputFilter { source, _, _, _, _, _ ->
            if (source != null && blockCharacterSet.contains("" + source)) {
                ""
            } else null
        }
    val filterLength = InputFilter.LengthFilter(10)
    filters = arrayOf(filterSpecialChar,filterLength)
}
