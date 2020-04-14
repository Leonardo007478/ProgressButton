package com.leonardogub.progressbutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity.CENTER
import android.view.Gravity.NO_GRAVITY
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import me.zhanghai.android.materialprogressbar.MaterialProgressBar


class ProgressButton : FrameLayout {

    private var button: TextView = TextView(context)
    private var mButtonText: String? = null
    private lateinit var progressBar: MaterialProgressBar

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initializeButton(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, intDefStyle: Int) : super(
        context,
        attrs,
        intDefStyle
    ) {
        initializeButton(attrs)
    }

    var isProgressVisible: Boolean
        get() = progressBar.visibility == View.VISIBLE
        set(value) {
            if (value) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        }

    private fun initializeButton(attrs: AttributeSet?) {

        isClickable = true
        isFocusable = true

        val taPb = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton)

        //Button text size
        if (taPb.hasValue(R.styleable.ProgressButton_pb_textSize)) {
            val fontSize = taPb.getDimensionPixelSize(R.styleable.ProgressButton_pb_textSize, 14)
            button.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize.toFloat())
        }

        //Button font family
        if (taPb.hasValue(R.styleable.ProgressButton_pb_fontFamily)) {
            val fontFamily = taPb.getResourceId(R.styleable.ProgressButton_pb_fontFamily, -1)
            button.typeface = ResourcesCompat.getFont(context, fontFamily)
        }

        //Button text color
        button.setTextColor(taPb.getColor(R.styleable.ProgressButton_pb_textColor, Color.BLACK))

        //Button text
        mButtonText = taPb.getString(R.styleable.ProgressButton_pb_text)?.apply {
            button.text = this
        }

        //Progress bar tint
        val isLoading = taPb.getBoolean(R.styleable.ProgressButton_pb_isLoading, false)
        val progressTint = taPb.getColor(R.styleable.ProgressButton_pb_loaderTint, Color.BLACK)
        val progressBarSize = taPb.getDimensionPixelSize(
            R.styleable.ProgressButton_pb_progressSize,
            dpToPx(25).toInt()
        )

        taPb.recycle()

        button.gravity = CENTER
        button.layoutParams =
            LayoutParams(MATCH_PARENT, MATCH_PARENT, CENTER)
        button.isAllCaps = false

        addView(button)

        progressBar = MaterialProgressBar(context)
        progressBar.layoutParams = LayoutParams(progressBarSize, progressBarSize, CENTER)
        progressBar.supportIndeterminateTintList = ColorStateList.valueOf(progressTint)
        addView(progressBar)

        progressBar.scaleX = 0f
        progressBar.scaleY = 0f

        ViewCompat.setElevation(progressBar, ViewCompat.getElevation(button) + dpToPx(10))

        if(isLoading) {
            showProgressBar()
        } else {
            hideProgressBar()
        }
    }

    private fun dpToPx(dp: Int) = dp * resources.displayMetrics.density

    fun showProgressBar() {
        progressBar.animate().scaleX(1f).scaleY(1f).setDuration(300).start()
        button.text = ""
        isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar.animate().scaleX(0f).scaleY(0f).setDuration(300)
            .withEndAction {
                progressBar.visibility = View.INVISIBLE
                isEnabled = true
                button.text = mButtonText
            }
            .start()
    }

//    override fun setOnClickListener(l: OnClickListener?) {
//        button.setOnClickListener(l)
//    }
//
//    override fun hasOnClickListeners(): Boolean {
//        return button.hasOnClickListeners()
//    }

    fun setText(text: String?) {
        mButtonText = text
        if (isEnabled) {
            button.text = text
        }
    }
}
