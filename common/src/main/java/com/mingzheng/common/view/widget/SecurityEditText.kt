package com.mingzheng.common.view.widget

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import java.lang.reflect.Method

class SecurityEditText : AppCompatEditText {

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleRes
    ) {
        initialize()
    }

    private fun initialize() {
        this.isClickable = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showSoftInputOnFocus = false
        } else {
            try {
                val cls = EditText::class.java
                val setShowSoftInputOnFocus: Method
                setShowSoftInputOnFocus =
                    cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
                setShowSoftInputOnFocus.isAccessible = true
                setShowSoftInputOnFocus.invoke(this, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            hideSystemKeyboard()
        }
    }

    override fun performClick(): Boolean {
        if (this.isFocused) {
            hideSystemKeyboard()
        }
        return false
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (hasWindowFocus && hasFocus()) {
            this.post {
                hideSystemKeyboard()
            }
        }
    }


/*隐藏系统键盘*/
    private fun hideSystemKeyboard() {
        val manager = this.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}