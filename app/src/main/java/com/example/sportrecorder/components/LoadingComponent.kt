package com.example.sportrecorder.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.sportrecorder.databinding.ComponentLoadingBinding

class LoadingComponent @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        ComponentLoadingBinding.inflate(
            LayoutInflater.from(context), this, true)
    }

    fun show() {
        visibility = VISIBLE
    }

    fun hide() {
        visibility = GONE
    }

    fun setVisibility(newVisibility: Boolean) {
        visibility = if (newVisibility) VISIBLE else GONE
    }
}