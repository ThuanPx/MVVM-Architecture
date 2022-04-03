package com.thuanpx.mvvm_architecture.widget.dialogManager

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.thuanpx.ktext.view.clicks
import com.thuanpx.mvvm_architecture.base.fragment.BaseDialogFragment
import com.thuanpx.mvvm_architecture.base.viewmodel.EmptyViewModel
import com.thuanpx.mvvm_architecture.databinding.DialogAlertBinding

class DialogAlert : BaseDialogFragment<EmptyViewModel, DialogAlertBinding>(EmptyViewModel::class) {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogAlertBinding {
        return DialogAlertBinding.inflate(inflater, container, false)
    }

    var listener: OnButtonClickedListener? = null
    private var title: String? = ""
    private var message: String? = ""
    private var titleBtn: String? = ""

    override fun initialize() {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        arguments?.let {
            title = it.getString(
                TITLE_EXTRA
            )
            message = it.getString(
                MESSAGE_EXTRA
            )
            titleBtn = it.getString(
                TITLE_BUTTON_EXTRA
            )
        }

        viewBinding.run {
            tvTitle.text = title
            tvContent.text = message
            if (!titleBtn.isNullOrEmpty()) {
                btnPositive.text = titleBtn
            }
            if (!title.isNullOrEmpty()) {
                tvTitle.visibility = View.GONE
            }
            btnPositive.clicks {
                dismiss(); listener?.onPositiveClicked()
            }
        }
    }

    companion object {
        private const val TITLE_EXTRA = "TITLE_EXTRA"
        private const val MESSAGE_EXTRA = "MESSAGE_EXTRA"
        private const val TITLE_BUTTON_EXTRA = "TITLE_BUTTON_EXTRA"

        fun newInstance(
            title: String,
            message: String,
            titleBtn: String,
            listener: OnButtonClickedListener?
        ): DialogAlert {
            return DialogAlert().apply {
                arguments = Bundle().apply {
                    putString(
                        TITLE_EXTRA, title
                    )
                    putString(
                        MESSAGE_EXTRA, message
                    )
                    putString(
                        TITLE_BUTTON_EXTRA, titleBtn
                    )
                }
                this.listener = listener
            }
        }

        interface OnButtonClickedListener {
            fun onPositiveClicked()
        }
    }
}
