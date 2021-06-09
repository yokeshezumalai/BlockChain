package com.blockchain.app.widgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.blockchain.app.R


class BCTitleBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var title: String? = null
    private var backgroundColor: Drawable? = null
    private var extraIcon: Drawable? = null
    private var extraTextButton: String? = null
    private var extraIconShow = false
    private var backIconHide = false
    private var secondIcon: Drawable? = null
    private var secondIconShow = false

    var titleTextView: TextView? = null
    var backIconImageView: ImageView? = null
    var extraIconImageView: ImageView? = null
    var secondIconImageView: ImageView? = null

    private var callbackListener: Callback? = null

    init {

        prepareAttributes(attrs)
        prepareLayout()

    }

    private fun prepareAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val attrs = context.obtainStyledAttributes(it, R.styleable.BCTitleBar, 0, 0)
            try {
                title = attrs.getString(R.styleable.BCTitleBar_titleText)
                backgroundColor = attrs.getDrawable(R.styleable.BCTitleBar_titleBarBackgroundColor)
                extraIcon = attrs.getDrawable(R.styleable.BCTitleBar_extraIcon)
                extraTextButton = attrs.getString(R.styleable.BCTitleBar_extraTextButton)
                extraIconShow = attrs.getBoolean(R.styleable.BCTitleBar_extraIconShow, false)
                backIconHide = attrs.getBoolean(R.styleable.BCTitleBar_backIconHide, false)
                secondIcon = attrs.getDrawable(R.styleable.BCTitleBar_secondIcon)
                secondIconShow = attrs.getBoolean(R.styleable.BCTitleBar_secondIconShow, false)
            } finally {
                attrs.recycle()
            }
        }
    }

    private fun prepareLayout() {
        val root = LayoutInflater.from(context).inflate(R.layout.custom_title_bar, this, true)
        titleTextView = rootView.findViewById(R.id.title)
        backIconImageView = rootView.findViewById(R.id.startIcon)
        extraIconImageView =  rootView.findViewById(R.id.endIcon)


        title?.let { titleTextView?.text = it }

        backgroundColor?.let { root.background = it }


        backIconImageView?.visibility = if (!backIconHide) View.VISIBLE else View.GONE
        extraIconImageView?.visibility = if (extraIconShow && extraTextButton.isNullOrBlank()) View.VISIBLE else View.GONE

        extraIconImageView?.setImageDrawable(if (extraIcon != null) extraIcon else ContextCompat.getDrawable(context, R.drawable.ic_filter))


        backIconImageView?.setOnClickListener {
            val navController = Navigation.findNavController(root)
            navController.navigateUp()
        }
    }

    fun hideExtraButton() {
        extraIconImageView?.visibility = GONE
    }

    fun hideBackButton() {
        backIconImageView?.visibility = GONE
    }


    @UiThread
    fun setTitleText(text: String?) {
        text?.let { titleTextView?.text = it }
        requestLayout()
    }

    fun setListener(listener: Callback) {
        callbackListener = listener

        callbackListener?.let { callback ->
            extraIconImageView?.setOnClickListener {
                callback.onExtraButtonClick()
            }
            backIconImageView?.setOnClickListener {
                callback.onStartButtonClick()
            }
            secondIconImageView?.setOnClickListener {
                callback.onSecondButtonClick()
            }
        }
    }

    interface Callback {
        fun onStartButtonClick() {}
        fun onExtraButtonClick() {}
        fun onSecondButtonClick() {}
    }


}