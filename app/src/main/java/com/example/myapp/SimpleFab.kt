package com.example.myapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

/*

use:
<com.yourpackage.SimpleFab
    android:layout_width="56dp"
    android:layout_height="56dp"
    app:bgSrc="@drawable/fab_background"
    app:iconSrc="@drawable/ic_add_24" />

custom attrs:
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="SimpleFab">
        <attr name="bgSrc" format="reference" />
        <attr name="iconSrc" format="reference" />
    </declare-styleable>
</resources>

exe:
package com.yourpackage

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat

class SimpleFab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleFab)

            // Read the drawables from the XML attributes
            val bgResId = typedArray.getResourceId(R.styleable.SimpleFab:bgSrc, 0)
            val iconResId = typedArray.getResourceId(R.styleable.SimpleFab:iconSrc, 0)

            if (bgResId != 0 && iconResId != 0) {
                val bgDrawable = ContextCompat.getDrawable(context, bgResId)
                val iconDrawable = ContextCompat.getDrawable(context, iconResId)

                if (bgDrawable != null && iconDrawable != null) {
                    setupLayeredDrawable(bgDrawable, iconDrawable)
                }
            }

            typedArray.recycle()
        }
    }

    private fun setupLayeredDrawable(background: Drawable, icon: Drawable) {
        // Put them into an array (ordered bottom to top)
        val layers = arrayOf(background, icon)
        val layerDrawable = LayerDrawable(layers)

        // Prevent the icon from stretching to the edges of the FAB background
        // Adds 16dp of padding all around the top icon layer
        val paddingInPx = (16 * context.resources.displayMetrics.density).toInt()
        layerDrawable.setLayerInset(1, paddingInPx, paddingInPx, paddingInPx, paddingInPx)

        // Set the final composite drawable as the image source
        setImageDrawable(layerDrawable)

        // Ensure it centers properly
        scaleType = ScaleType.CENTER_INSIDE
    }
}

to animate:
fun hideSmoothly() {
    animate()
        .alpha(0f)
        .scaleX(0f)
        .scaleY(0f)
        .setDuration(200)
        .withEndAction { visibility = View.GONE }
        .start()
}

fun showSmoothly() {
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .scaleX(1f)
        .scaleY(1f)
        .setDuration(200)
        .withEndAction(null)
        .start()
}



 */

class SimpleFab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleFab)

            // Read the drawables from the XML attributes
            val bgResId = typedArray.getResourceId(R.styleable.SimpleFab_bgSrc, 0)
            val iconResId = typedArray.getResourceId(R.styleable.SimpleFab_iconSrc, 0)

            if (bgResId != 0 && iconResId != 0) {
                val bgDrawable = ContextCompat.getDrawable(context, bgResId)
                val iconDrawable = ContextCompat.getDrawable(context, iconResId)

                if (bgDrawable != null && iconDrawable != null) {
                    setupLayeredDrawable(bgDrawable, iconDrawable)
                }
            }

            typedArray.recycle()
        }
    }

    private fun setupLayeredDrawable(background: Drawable, icon: Drawable) {
        // Put them into an array (ordered bottom to top)
        val layers = arrayOf(background, icon)
        val layerDrawable = LayerDrawable(layers)

        // Prevent the icon from stretching to the edges of the FAB background
        // Adds 16dp of padding all around the top icon layer
        val paddingInPx = (16 * context.resources.displayMetrics.density).toInt()
        layerDrawable.setLayerInset(1, paddingInPx, paddingInPx, paddingInPx, paddingInPx)

        // Set the final composite drawable as the image source
        setImageDrawable(layerDrawable)

        // Ensure it centers properly
        scaleType = ScaleType.CENTER_INSIDE
    }
    fun hide() {
        animate()
            .alpha(0f)
            .scaleX(0f)
            .scaleY(0f)
            .setDuration(200)
            .withEndAction { visibility = View.GONE }
            .start()
    }

    fun show() {
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(200)
            .withEndAction(null)
            .start()
    }

    fun setLayeredAssets(backgroundResId: Int, iconResId: Int) {
        val bgDrawable = ContextCompat.getDrawable(context, backgroundResId)
        val iconDrawable = ContextCompat.getDrawable(context, iconResId)
        if (bgDrawable != null && iconDrawable != null) {
            if (visibility == View.VISIBLE) {
                animate().alpha(0f).scaleX(0f).scaleY(0f).setDuration(200)
                    .withEndAction {
                        visibility = View.INVISIBLE
                        setupLayeredDrawable(bgDrawable, iconDrawable)
                        animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(200)
                            .withEndAction { View.VISIBLE }
                            .start()
                    }.start()
            }
            else {
                setupLayeredDrawable(bgDrawable, iconDrawable)
                animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(200)
                    .withEndAction { View.VISIBLE }
                    .start()
            }
        }
    }
}