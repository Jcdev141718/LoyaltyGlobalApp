@file:Suppress("BlockingMethodInNonBlockingContext")

package com.loyaltyglobal.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.*
import android.location.Location
import android.os.Build
import android.os.SystemClock
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.loyaltyglobal.R
import com.loyaltyglobal.util.Constants.REGEX_EMAIL
import com.loyaltyglobal.util.customCookieView.cookiebar2.CookieBar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import kotlin.math.roundToInt


/**
 * File holding all the methods of general interest.
 * Create by Abhin.
 */

/**
 * This method converts dp units to pixels.
 *
 * @param dp        the wanted dp units.
 * @param resources the application's `Resources`
 * @return The corresponding pixel value.
 */
fun dpToPx(dp: Float, resources: Resources): Int {
    return convertToPx(TypedValue.COMPLEX_UNIT_DIP, dp, resources)
}

/**
 * This method converts dp units to pixels.
 *
 * @param dp        the wanted dp units.
 * @param resources the application's `Resources`
 * @return The corresponding pixel value.
 */
fun dpToPxFloat(dp: Float, resources: Resources): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

/**
 * This method converts any unit to pixels.
 *
 * @param unit      the wanted unit.
 * @param value     the wanted value units.
 * @param resources the application's `Resources`
 * @return The corresponding pixel value.
 */
private fun convertToPx(unit: Int, value: Float, resources: Resources): Int {
    val px = TypedValue.applyDimension(unit, value, resources.displayMetrics)
    return px.toInt()
}


fun isNotch(resources: Resources): Boolean {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    val statusBarHeight = if (resourceId > 0) {
        resources.getDimensionPixelSize(resourceId)
    } else 0
    return statusBarHeight > dpToPx(24f, resources)
}

/**
 * Add replace fragment
 *
 * @param container
 * @param fragment
 * @param addFragment
 * @param addToBackStack
 */
fun FragmentActivity.addReplaceFragment(
    @IdRes container: Int,
    fragment: Fragment,
    addFragment: Boolean,
    addToBackStack: Boolean,
) {
    val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    if (addFragment) {
        transaction.add(container, fragment, fragment.javaClass.simpleName)
    } else {
        transaction.replace(container, fragment, fragment.javaClass.simpleName)
    }
    if (addToBackStack) {
        transaction.addToBackStack(fragment.tag)
    }
    hideKeyboard()
    transaction.commit()
}

fun FragmentActivity.popFragment() {
    supportFragmentManager.popBackStack()
}

/**
 * Add replace fragment
 *
 * @param container
 * @param fragment
 * @param addFragment
 * @param addToBackStack
 */
fun FragmentActivity.addReplaceFragmentWithAnimation(
    @IdRes container: Int,
    fragment: Fragment,
    addFragment: Boolean,
    addToBackStack: Boolean,
    enterAnimation: Int,
    exitAnimation: Int,
) {
    val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(enterAnimation, exitAnimation)
    if (addFragment) {
        transaction.add(container, fragment, fragment.javaClass.simpleName)
    } else {
        transaction.replace(container, fragment, fragment.javaClass.simpleName)
    }
    if (addToBackStack) {
        transaction.addToBackStack(fragment.tag)
    }
    hideKeyboard()
    transaction.commit()
}

/**Get current active Fragment in a container of a activity
 * @param container -> Container holder id  of fragment of a activity
 * **/
fun AppCompatActivity.getCurrentFragment(@IdRes container: Int): Fragment? = supportFragmentManager.findFragmentById(container)

fun Activity.hideSystemUI() {
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setDecorFitsSystemWindows(false)
            val controller = decorView.windowInsetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            statusBarColor = Color.TRANSPARENT
        } else {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}

fun Activity.statusBarDarkIcons(color: Int? = null, isLightStatusBar: Boolean = true) {
    window.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if (controller != null) {
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                isLightStatusBar -> {
                    decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                }
            }
            statusBarColor = color ?: ContextCompat.getColor(context, R.color.colorPrimary)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            statusBarColor = color ?: ContextCompat.getColor(context, R.color.colorPrimary)
        } else {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}

fun AppCompatEditText.isEmailValid(): Boolean {
    val matcher = Pattern.compile(REGEX_EMAIL).matcher(text?.trim().toString())
    return matcher.matches()
}

//hide the keyboard
fun Activity.hideKeyboard() {
    val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = currentFocus
    if (view == null) view = View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

//show the keyboard
fun Activity.showKeyboard() {
    val view = currentFocus
    val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun View.clickWithDebounce(debounceTime: Long = 1200L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

class RecyclerItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val spacing = (spacing * parent.context.resources.displayMetrics.density).roundToInt()
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount

        outRect.top = if (position < spanCount) spacing else 0
        outRect.bottom = spacing
    }
}

fun hasPermissions(
    context: Context?, permissions: Array<String>?,
): Boolean {
    if (context != null && !permissions.isNullOrEmpty()) {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
    }
    return true
}

fun FragmentActivity.showTopSnackBar(title: String, message: String) {
    CookieBar.build(this).setCustomView(R.layout.view_snackbar_cookie).setCustomViewInitializer { v ->
        val tvTitle = v.findViewById<AppCompatTextView>(R.id.tv_title)
        if (title.isEmpty()) {
            tvTitle.hide()
        } else {
            tvTitle.show()
        }
    }.setTitle(title).setMessage(message).setEnableAutoDismiss(true).setSwipeToDismiss(false).setCookiePosition(Gravity.TOP)
        .show()
}

fun checkRefreshThreshold(storedTime: Long): Boolean {
    val storeDate = Date(storedTime)
    val difference = Date().time - storeDate.time
    val diffInMinute = TimeUnit.MILLISECONDS.toMinutes(difference)
    return diffInMinute > 15
}


open class NoUnderlineClickSpan(val context: Context) : ClickableSpan() {
    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.isUnderlineText = false
        textPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
    }

    override fun onClick(widget: View) {}
}

@SuppressLint("SetTextI18n")
@Suppress("DEPRECATION") // It has been replaced by a Builder, which is minAPI 28, so OK for now
fun TextView.setResizableText(
    fullText: String,
    maxLines: Int,
    viewMore: Boolean,
    applyExtraHighlights: ((Spannable) -> (Spannable))? = null,
) {
    val width = width
    if (width <= 0) {
        post {
            setResizableText(fullText, maxLines, viewMore, applyExtraHighlights)
        }
        return
    }
    movementMethod =
        LinkMovementMethod.getInstance() // Since we take the string character by character, we don't want to break up the Windows-style
    // line endings.
    val adjustedText = fullText.replace("\r\n", "\n") // Check if even the text has to be resizable.
    val textLayout = StaticLayout(adjustedText,
        paint,
        width - paddingLeft - paddingRight,
        Layout.Alignment.ALIGN_NORMAL,
        lineSpacingMultiplier,
        lineSpacingExtra,
        includeFontPadding)
    if (textLayout.lineCount <= maxLines || adjustedText.isEmpty()) { // No need to add 'read more' / 'read less' since the text fits just as well (less than max lines #).
        val htmlText = adjustedText.replace("\n", "<br/>")
        text = addClickablePartTextResizable(fullText,
            maxLines,
            HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT),
            null,
            viewMore,
            applyExtraHighlights)
        return
    }
    val charactersAtLineEnd = textLayout.getLineEnd(maxLines - 1)
    val suffixText = if (viewMore) resources.getString(R.string.see_more) else resources.getString(R.string.see_less)
    var charactersToTake = charactersAtLineEnd - suffixText.length / 2 // Good enough first guess
    if (charactersToTake <= 0) { // Happens when text is empty
        val htmlText = adjustedText.replace("\n", "<br/>")
        text = addClickablePartTextResizable(fullText,
            maxLines,
            HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT),
            null,
            viewMore,
            applyExtraHighlights)
        return
    }
    if (!viewMore) { // We can set the text immediately because nothing needs to be measured
        val htmlText = adjustedText.replace("\n", "<br/>")
        text = addClickablePartTextResizable(fullText,
            maxLines,
            HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT),
            suffixText,
            viewMore,
            applyExtraHighlights)
        return
    }
    val lastHasNewLine =
        adjustedText.substring(textLayout.getLineStart(maxLines - 1), textLayout.getLineEnd(maxLines - 1)).contains("\n")
    val linedText = if (lastHasNewLine) {
        val charactersPerLine = textLayout.getLineEnd(0) / (textLayout.getLineWidth(0) / textLayout.ellipsizedWidth.toFloat())
        val lineOfSpaces =
            "\u00A0".repeat(charactersPerLine.roundToInt()) // non breaking space, will not be thrown away by HTML parser
        charactersToTake += lineOfSpaces.length - 1
        adjustedText.take(textLayout.getLineStart(maxLines - 1)) + adjustedText.substring(textLayout.getLineStart(maxLines - 1),
            textLayout.getLineEnd(maxLines - 1)).replace("\n", lineOfSpaces) + adjustedText.substring(textLayout.getLineEnd(
            maxLines - 1))
    } else {
        adjustedText
    } // Check if we perhaps need to even add characters? Happens very rarely, but can be possible if there was a long word just wrapped
    val shortenedString = linedText.take(charactersToTake)
    val shortenedStringWithSuffix = shortenedString + suffixText
    val shortenedStringWithSuffixLayout = StaticLayout(shortenedStringWithSuffix,
        paint,
        width - paddingLeft - paddingRight,
        Layout.Alignment.ALIGN_NORMAL,
        lineSpacingMultiplier,
        lineSpacingExtra,
        includeFontPadding)
    val modifier: Int
    if (shortenedStringWithSuffixLayout.getLineEnd(maxLines - 1) >= shortenedStringWithSuffix.length) {
        modifier = 1
        charactersToTake-- // We might just be at the right position already
    } else {
        modifier = -1
    }
    do {
        charactersToTake += modifier
        val baseString = linedText.take(charactersToTake)
        val appended = baseString + suffixText
        val newLayout = StaticLayout(appended,
            paint,
            width - paddingLeft - paddingRight,
            Layout.Alignment.ALIGN_NORMAL,
            lineSpacingMultiplier,
            lineSpacingExtra,
            includeFontPadding)
    } while ((modifier < 0 && newLayout.getLineEnd(maxLines - 1) < appended.length) || (modifier > 0 && newLayout.getLineEnd(
            maxLines - 1) >= appended.length)
    )
    if (modifier > 0) {
        charactersToTake-- // We went overboard with 1 char, fixing that
    } // We need to convert newlines because we are going over to HTML now
    val htmlText = linedText.take(charactersToTake).replace("\n", "<br/>")
    text = addClickablePartTextResizable(fullText,
        maxLines,
        HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT),
        suffixText,
        viewMore,
        applyExtraHighlights)
}


private fun TextView.addClickablePartTextResizable(
    fullText: String,
    maxLines: Int,
    shortenedText: Spanned,
    clickableText: String?,
    viewMore: Boolean,
    applyExtraHighlights: ((Spannable) -> (Spannable))? = null,
): Spannable {
    val builder = SpannableStringBuilder(shortenedText)
    if (clickableText != null) {
        builder.append(clickableText)
        val startIndexOffset = if (viewMore) 4 else 0 // Do not highlight the 3 dots and the space
        builder.setSpan(object : NoUnderlineClickSpan(context) {
            override fun onClick(widget: View) {
                if (viewMore) {
                    setResizableText(fullText, maxLines, false, applyExtraHighlights)
                } else {
                    setResizableText(fullText, maxLines, true, applyExtraHighlights)
                }
            }
        }, builder.indexOf(clickableText) + startIndexOffset, builder.indexOf(clickableText) + clickableText.length, 0)
    }
    if (applyExtraHighlights != null) {
        return applyExtraHighlights(builder)
    }
    return builder
}

val locationPermissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

fun getDistance(userLatLng: LatLng, brandLatLng: LatLng): String {
    val locationA = Location("User Location").apply {
        latitude = userLatLng.latitude
        longitude = userLatLng.longitude
    }
    val locationB = Location("Brand Location").apply {
        latitude = brandLatLng.latitude
        longitude = brandLatLng.longitude
    }
    return ((locationA.distanceTo(locationB)) / 1000).toInt().toString() + " kilometers away"
}

suspend fun createCustomMarker(context: Context, imageUrl: String): Bitmap? {
    val bitmap: Bitmap?
    val marker =
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.custom_marker_layout, null)
    val markerImage = marker.findViewById(R.id.user_dp) as CircleImageView
    try {
        val imageBitMap = withContext(Dispatchers.IO) {
            getBitmapFromUrl(imageUrl)
        }
        markerImage.setImageBitmap(imageBitMap)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val displayMetrics = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
    marker.layoutParams = ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT)
    marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
    marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
    marker.buildDrawingCache()
    bitmap = Bitmap.createBitmap(marker.measuredWidth, marker.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap!!)
    marker.draw(canvas)
    return bitmap
}


suspend fun getBitmapFromUrl(imageUrl: String): Bitmap {
    val url = URL(imageUrl)
    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
    conn.doInput = true
    conn.connect()
    val inputStream: InputStream = conn.inputStream
    return BitmapFactory.decodeStream(inputStream)
}