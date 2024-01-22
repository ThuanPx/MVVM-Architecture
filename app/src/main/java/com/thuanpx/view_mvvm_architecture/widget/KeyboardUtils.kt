package com.thuanpx.mvvm_architecture.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

@SuppressLint("ClickableViewAccessibility")
object KeyboardUtils {
    fun setupKeyBoard(view: View) {
        if (view !is EditText || !view.isFocusable()) {
            view.setOnTouchListener { arg0: View?, _: MotionEvent? ->
                hideSoftKeyboard(arg0)
                false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupKeyBoard(innerView)
            }
        }
    }

    fun isKeyBoard(isShow: Boolean): Boolean {
        return isShow
    }

    fun setupKeyBoard(activity: Activity) {
        setupKeyBoard(activity.window.decorView.rootView)
    }

    fun showSoftKeyboard(activity: Activity) {
        showSoftKeyboard(activity.window.decorView.rootView)
    }

    fun hideSoftKeyboard(activity: Activity) {
        hideSoftKeyboard(activity.window.decorView.rootView)
    }

    fun showSoftKeyboard(view: View?) {
        if (view != null) {
            val inputManager = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.toggleSoftInput(
                InputMethodManager.HIDE_IMPLICIT_ONLY,
                0
            )
        }
    }

    fun hideSoftKeyboard(view: View?) {
        if (view != null) {
            val inputManager = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
