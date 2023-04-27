package com.elsa.giantbomb.utility

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * Get date string from time string
 * E.g "2018-07-25 06:22:22" -> "2018-07-25"
 */
fun String.getDateOnly(): String {
    val index = this.indexOf(' ');
    return if(index < 0) {
        this
    } else {
        this.substring(0, index)
    }
}

/**
 * @return Html type spanned string highlighting keyword
 */
fun String.convertToHighlightedHtmlString(highlight: String): Spanned {
    val regexInput = "(?i)($highlight)"
    // Use light green color
    val color = "#90EE90"
    val replaceString = "<font color='$color'>$1</font>"
    return Html.fromHtml(this.replace(regexInput.toRegex(), replaceString), Html.FROM_HTML_MODE_LEGACY)
}

@SuppressLint("CheckResult")
fun <T> RequestBuilder<T>.onEndLoading(onLoadingEnd: () -> Unit) = apply {
    listener(object : RequestListener<T> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<T>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingEnd()
            return false
        }

        override fun onResourceReady(
            resource: T?,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadingEnd()
            return false
        }
    })
}

fun swapVisibility(duration: Long, visibleView: View, vararg invisibleViews: View) {
    visibleView.alpha = 0f
    visibleView.visibility = View.VISIBLE

    val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
    valueAnimator.addUpdateListener {
        visibleView.alpha = it.animatedValue as Float
        invisibleViews.forEach { view ->
            if (view.isVisible) view.alpha = 1f - (it.animatedValue as Float)
        }
    }
    valueAnimator.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            invisibleViews.forEach { it.visibility = View.GONE}
        }
    })
    valueAnimator.duration = duration
    valueAnimator.start()
}
