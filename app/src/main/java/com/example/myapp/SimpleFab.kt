/*
 * Copyright (c) 2026 Your(kana-tutor.com
 * * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.myapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat



class SimpleFab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val userPrefs: SharedPreferences = context.getSharedPreferences(
        "SimpleFabPrefs", Context.MODE_PRIVATE)
    private val regex = """\((\d+),\s*(\d+)\)""".toRegex()

    fun String.toPair(): Pair<Int,Int> {
        val rv = regex.find(this)?.destructured?.let { (first, second) ->
            Pair(first.toInt(), second.toInt())
        }
        if(rv == null)
            throw Exception (
            "String.toPair: !ad string to pair conversion for \'4this\""
            )
        return rv
    }

    fun getIntPair(name: String, default: Pair<Int,Int>?): Pair<Int, Int>? {
        var rv: Pair<Int, Int>? = default
        val value = userPrefs.getString("$name.IntPair", default?.toString())
        if (value != null) {
            rv = value.toPair()
        }
        return rv
    }
    fun putIntPair(name: String, pair: Pair<Int, Int>?) {
        val s: String? = pair?.toString()
        userPrefs.edit().putString("$name.IntPair", s).apply()
    }

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleFab)

            // Read the drawables from the XML attributes
            val _bgResId = typedArray.getResourceId(R.styleable.SimpleFab_bgSrc, 0)
            val _iconResId = typedArray.getResourceId(R.styleable.SimpleFab_iconSrc, 0)
            // if the value wasn't set, set it.
            if (getIntPair("resId", null) == null) {
                putIntPair("resId", Pair(_bgResId, _iconResId))
            }
            // if this returns 0,0 (which shouln't happen) we fail below
            val (bgResId, iconResId) = getIntPair("resId", Pair(0,0))!!

            require (bgResId != 0 && iconResId != 0) {
                "SimpleFab: bgResId and iconResid must be set."
            }
            putIntPair("resId", Pair(bgResId, iconResId))

            val bgDrawable = ContextCompat.getDrawable(context, bgResId)
            val iconDrawable = ContextCompat.getDrawable(context, iconResId)

            require (bgDrawable != null && iconDrawable != null) {
                "String: Bad conversion: setupLayeredDrawable(" +
                    "$bgDrawable, $iconDrawable)\n"
            }
            setupLayeredDrawable(bgDrawable, iconDrawable)
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

    fun setLayeredAssets(bgResId: Int, iconResId: Int) {
        // save the new res ids.
        putIntPair("resId", Pair(bgResId, iconResId))
        val bgDrawable = ContextCompat.getDrawable(context, bgResId)
        val iconDrawable = ContextCompat.getDrawable(context, iconResId)
        if (bgDrawable != null && iconDrawable != null) {
            if (visibility == View.VISIBLE) {
                animate().alpha(0f).scaleX(0f).scaleY(0f).setDuration(200)
                    .withEndAction {
                        // 1. Swap assets while hidden
                        setupLayeredDrawable(bgDrawable, iconDrawable)

                        // 2. Make it visible FIRST so the user can watch it grow
                        // visibility = View.VISIBLE

                        // 3. Animate it back up
                        animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(200)
                            .withEndAction(null) // Clear the action loop cleanly
                            .start()
                    }.start()
            }
            else {
                // It's hidden/GONE, so update it instantly
                setupLayeredDrawable(bgDrawable, iconDrawable)

                // Bring it back smoothly
                visibility = View.VISIBLE
                animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(200)
                    .withEndAction(null)
                    .start()
            }
        }
    }
}
