package com.thuanpx.view_mvvm_architecture.utils.extension.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thuanpx.view_mvvm_architecture.R
import com.thuanpx.view_mvvm_architecture.utils.extension.view.clicks
import com.thuanpx.view_mvvm_architecture.utils.extension.view.gone

/**
 * Created by ThuanPx on 15/09/2021.
 */

@DslDialog
fun Fragment.dialog(setup: DialogBuilder.() -> Unit) {
    val builder = DialogBuilder(requireContext(), setup = setup)
    builder.build().show()
}

@DslDialog
fun FragmentActivity.dialog(setup: DialogBuilder.() -> Unit) {
    val builder = DialogBuilder(this, setup = setup)
    builder.build().show()
}

data class DialogOptions(
    val title: String,
    val message: String,
    val positiveText: String,
    val negativeText: String,
    val positiveListener: (() -> Unit)? = null,
    val negativeListener: (() -> Unit)? = null,
    var positiveColor: Int,
    var negativeColor: Int,
    var messageColor: Int,
    var titleColor: Int,
    val cancelable: Boolean,
    val isShowNegative: Boolean
)

@DslMarker
annotation class DslDialog

@DslDialog
class DialogBuilder(
    private val context: Context,
    val setup: DialogBuilder.() -> Unit = {}
) {

    var title: String = ""
    var message: String = ""
    var titleColor: Int = android.R.color.black
    var messageColor: Int = android.R.color.black
    var positiveText: String = "OK"
    var negativeText: String = "No"
    var positiveListener: (() -> Unit)? = null
    var negativeListener: (() -> Unit)? = null
    var positiveColor: Int = R.color.blue_700
    var negativeColor: Int = R.color.blue_700
    var cancelable: Boolean = false
    var isShowNegative = false
    private lateinit var dialog: AlertDialog

    fun build(): AlertDialog {
        setup()
        if (message.isEmpty()) {
            throw IllegalArgumentException("You should fill all mandatory fields in the options")
        }
        val options = DialogOptions(
            title = title,
            message = message,
            positiveText = positiveText,
            negativeText = negativeText,
            positiveListener = positiveListener,
            negativeListener = negativeListener,
            cancelable = cancelable,
            isShowNegative = isShowNegative,
            titleColor = titleColor,
            messageColor = messageColor,
            negativeColor = negativeColor,
            positiveColor = positiveColor
        )

        dialog = setupCustomAlertDialog(options)

        return dialog
    }

    private fun setupCustomAlertDialog(options: DialogOptions): AlertDialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null)

        val alertDialog =
            MaterialAlertDialogBuilder(context, R.style.DialogCustomTheme)
                .setView(view)
                .setCancelable(options.cancelable)
                .create()

        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = options.title
        tvTitle.setTextColor(ContextCompat.getColor(context, options.titleColor))
        tvTitle.gone(isGone = options.title.isEmpty())

        val tvMessage = view.findViewById<TextView>(R.id.tvMessage)
        tvMessage.text = options.message
        tvMessage.setTextColor(ContextCompat.getColor(context, options.messageColor))

        val buttonNegative = view.findViewById<TextView>(R.id.btNegative)
        buttonNegative.setTextColor(ContextCompat.getColor(context, options.negativeColor))
        buttonNegative.visibility = if (isShowNegative) View.VISIBLE else View.GONE
        buttonNegative.text = options.negativeText
        buttonNegative.clicks {
            options.negativeListener?.invoke()
            if (alertDialog.isShowing) {
                alertDialog.dismiss()
            }
        }

        val buttonPositive = view.findViewById<TextView>(R.id.btPositive)
        buttonPositive.setTextColor(ContextCompat.getColor(context, options.positiveColor))
        buttonPositive.text = options.positiveText
        buttonPositive.clicks {
            options.positiveListener?.invoke()
            if (alertDialog.isShowing) {
                alertDialog.dismiss()
            }
        }

        return alertDialog
    }
}
