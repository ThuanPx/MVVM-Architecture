package com.thuanpx.mvvm_architecture.widget.dialogManager

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thuanpx.ktext.view.gone
import com.thuanpx.mvvm_architecture.base.BaseDialogFragment
import com.thuanpx.mvvm_architecture.base.EmptyViewModel
import com.thuanpx.mvvm_architecture.databinding.DialogConfirmBinding
import com.thuanpx.mvvm_architecture.utils.extension.clicks

class DialogConfirm : BaseDialogFragment<EmptyViewModel, DialogConfirmBinding>(EmptyViewModel::class) {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogConfirmBinding {
        return DialogConfirmBinding.inflate(inflater, container, false)
    }

    var listener: OnButtonClickedListener? = null
    private var title: String? = ""
    private var message: String? = ""
    private var titleBtnPositive: String? = ""
    private var titleBtnNegative: String? = ""

    override fun initialize() {
        arguments?.let {
            title = it.getString(
                TITLE_EXTRA
            )
            message = it.getString(
                MESSAGE_EXTRA
            )
            titleBtnPositive = it.getString(
                TITLE_BUTTON_POSITIVE_EXTRA
            )
            titleBtnNegative = it.getString(
                TITLE_BUTTON_NEGATIVE_EXTRA
            )
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
            it.setCanceledOnTouchOutside(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tvTitle.text = title
        viewBinding.tvContent.text = message

        viewBinding.tvTitle.gone(title.isNullOrBlank())
        viewBinding.tvContent.gone(message.isNullOrBlank())

        viewBinding.btnPositive.clicks {
            listener?.onPositiveClicked()
        }
        viewBinding.btnNegative.clicks {
            listener?.onNegativeClicked()
        }
    }

    companion object {
        private const val TITLE_EXTRA = "TITLE_EXTRA"
        private const val MESSAGE_EXTRA = "MESSAGE_EXTRA"
        private const val TITLE_BUTTON_POSITIVE_EXTRA = "TITLE_BUTTON_POSITIVE_EXTRA"
        private const val TITLE_BUTTON_NEGATIVE_EXTRA = "TITLE_BUTTON_NEGATIVE_EXTRA"

        fun newInstance(
            title: String?,
            message: String?,
            titleBtnPositive: String,
            titleBtnNegative: String,
            listener: OnButtonClickedListener?
        ): DialogConfirm {
            return DialogConfirm().apply {
                arguments = Bundle().apply {
                    putString(
                        TITLE_EXTRA, title
                    )
                    putString(
                        MESSAGE_EXTRA, message
                    )
                    putString(
                        TITLE_BUTTON_POSITIVE_EXTRA, titleBtnPositive
                    )
                    putString(
                        TITLE_BUTTON_NEGATIVE_EXTRA, titleBtnNegative
                    )
                }
                this.listener = listener
            }
        }
    }

    interface OnButtonClickedListener {
        fun onPositiveClicked()
        fun onNegativeClicked()
    }
}
