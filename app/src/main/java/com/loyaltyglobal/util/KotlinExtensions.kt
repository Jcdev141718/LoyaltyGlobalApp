package com.loyaltyglobal.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.ParseException
import android.net.Uri
import android.text.InputFilter
import android.util.Log
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
import com.loyaltyglobal.R
import com.loyaltyglobal.app.LoyaltyGlobalApp
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by Abhin.
 */

fun String.isEmailValid(): Boolean = doesStringMatchPattern(this, Constants.REGEX_EMAIL)

fun String.isPasswordValid(): Boolean = doesStringMatchPattern(this, Constants.PASSWORD_PATTERN_WITH_ONE_SPECIAL_CHARS)

fun String.firstLetterCap(): String = this.substring(0, 1).uppercase(Locale.ROOT) + this.substring(1).lowercase(Locale.ROOT)


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
        Glide.with(getParentActivity()!!).load(url).circleCrop().into(this)
    } else {
        Glide.with(getParentActivity()!!).load(url).into(this)
    }
}

@BindingAdapter("setNotificationBg")
fun ConstraintLayout.setNotificationBg(isNotificationRead: Boolean = false) {
    if (!isNotificationRead) {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.main_card, value, true)
        this.setBackgroundColor(value.data)
    } else {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.black_white, value, true)
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

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.setFullHeight() {
    val layoutParams = this.layoutParams
    layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
    this.layoutParams = layoutParams
}

fun Fragment.openBottomSheet(bottomSheet: BottomSheetDialogFragment) {
    bottomSheet.show(this.requireActivity().supportFragmentManager, bottomSheet::class.java.simpleName)
}

fun EditText.preventSpecialCharacter() {
    val blockCharacterSet = "!#\$%&(){|}~:;<=>?@*+,./^_`-\\'\\\" \\t\\r\\n\\f]+ "
    val filterSpecialChar = InputFilter { source, _, _, _, _, _ ->
        if (source != null && blockCharacterSet.contains("" + source)) {
            ""
        } else null
    }
    val filterLength = InputFilter.LengthFilter(10)
    filters = arrayOf(filterSpecialChar, filterLength)
}

fun Context.dialPhoneNum(number: String) {
    startActivity(Intent(Intent.ACTION_DIAL).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        data = Uri.parse("tel:$number")
    })
}

fun Context.sendEmailTo(email: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    }
    try {
        startActivity(Intent.createChooser(intent, "Send mail..."))
    } catch (ex: ActivityNotFoundException) {
//        showToast("There are no email clients installed.")
    }
}

fun Long.covertTimeToText(): String? {
    var convertTime: String? = null
    val suffix = "ago"
    try {
        val nowTime = Date()
        val dateDiff = nowTime.time - this
        val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
        convertTime = if (second < 60) {
            if (second == 1L) {
                "$second second $suffix"
            } else {
                "$second seconds $suffix"
            }
        } else if (minute < 60) {
            if (minute == 1L) {
                "$minute minute $suffix"
            } else {
                "$minute minutes $suffix"
            }
        } else if (hour < 24) {
            if (hour == 1L) {
                "$hour hour $suffix"
            } else {
                "$hour hours $suffix"
            }
        } else if (day >= 7) {
            if (day >= 365) {
                val tempYear = day / 365
                if (tempYear == 1L) {
                    "$tempYear year $suffix"
                } else {
                    "$tempYear years $suffix"
                }
            } else if (day >= 30) {
                val tempMonth = day / 30
                if (tempMonth == 1L) {
                    (day / 30).toString() + " month " + suffix
                } else {
                    (day / 30).toString() + " months " + suffix
                }
            } else {
                val tempWeek = day / 7
                if (tempWeek == 1L) {
                    (day / 7).toString() + " week " + suffix
                } else {
                    (day / 7).toString() + " weeks " + suffix
                }
            }
        } else {
            if (day == 1L) {
                "$day day $suffix"
            } else {
                "$day days $suffix"
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        Log.e("TimeAgo", e.message.toString() + "")
    }
    return convertTime
}
