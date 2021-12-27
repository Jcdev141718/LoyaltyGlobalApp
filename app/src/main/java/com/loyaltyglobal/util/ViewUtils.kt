package com.loyaltyglobal.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Abhin.
 */

fun View.show() {
    visibility = View.VISIBLE
}

fun show(vararg views : View) {
    views.forEach {
        it.show()
    }
}

fun hide(vararg views : View) {
    views.forEach {
        it.hide()
    }
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                return view
            } else {
                fallback = view
            }
        }

        if (view != null) {
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)
    return fallback
}

/** Set the View visibility to VISIBLE and eventually animate the View alpha till 100% */
fun View.showWithFadeAnimation(duration : Long = 300) {
    animate().alpha(1f).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            super.onAnimationStart(animation)
            visibility = View.VISIBLE
        }
    })
}

fun View.showWithScaleAnimation(duration : Long = 300) {
    animate().scaleYBy(0f).scaleY(1f).setInterpolator(LinearInterpolator()).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            super.onAnimationStart(animation)
            visibility = View.VISIBLE
        }
    })
}

/** Set the View visibility to INVISIBLE and eventually animate view alpha till 0% */
fun View.invisibleWithAnimation(duration : Long = 300) {
    animate().alpha(0f).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            visibility = View.INVISIBLE
        }
    })
}

/** Set the View visibility to GONE and eventually animate view alpha till 0% */
fun View.hideWithFadeAnimation(duration : Long = 300, viewStrategyAfterAnim : Int = View.GONE) {
    alpha = 0.7f
    animate().alpha(0f).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            visibility = viewStrategyAfterAnim
        }
    })
}

fun View.hideWithScaleAnimation(duration : Long = 300,viewStrategyAfterAnim : Int = View.GONE) {
    animate().scaleYBy(1f).scaleY(0f).setInterpolator(DecelerateInterpolator()).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            visibility = viewStrategyAfterAnim
        }
    })
}

fun View.slideDownAndShowAnimation(duration: Long = 300) {
    visibility = View.VISIBLE
    alpha = 0.0f
    animate().translationY(0f).alpha(1.0f).setDuration(duration)
        .setInterpolator(DecelerateInterpolator())
        .setListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                visibility = View.VISIBLE
            }
        })
}

fun View.slideUpAndHideAnimation(duration: Long = 300, viewStrategyAfterAnim: Int = View.GONE) {
    visibility = View.VISIBLE
    animate()
        .translationY(-100f)
        .alpha(0f)
        .setInterpolator(DecelerateInterpolator())
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                visibility = viewStrategyAfterAnim
            }
        })
}

fun View.slideUp(duration: Long = 400,distance : Float) {
    visibility = View.VISIBLE
    animate()
        .translationY(-distance)
        .alpha(1f)
        .setInterpolator(DecelerateInterpolator())
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                visibility = View.VISIBLE
            }
        })
}

fun View.slideDown(duration: Long = 400) {
    visibility = View.VISIBLE
    animate()
        .translationY(0f)
        .alpha(1f)
        .setInterpolator(DecelerateInterpolator())
        .setDuration(duration)
        .setListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                visibility = View.VISIBLE
            }
        })
}

fun View.disable() {
    isEnabled = false
    alpha = 0.5f
    isClickable = false
}

fun View.enable() {
    isEnabled = true
    alpha = 1f
    isClickable = true
}
