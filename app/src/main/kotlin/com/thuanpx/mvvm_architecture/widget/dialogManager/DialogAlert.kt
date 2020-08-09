package com.thuanpx.mvvm_architecture.widget.dialogManager

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.thuanpx.mvvm_architecture.databinding.DialogAlertBinding
import kotlinx.android.synthetic.main.dialog_alert.*

class DialogAlert : DialogFragment() {

    private var _viewBinding: DialogAlertBinding? = null
    protected val viewBinding get() = _viewBinding!! // ktlint-disable

    var listener: OnButtonClickedListener? = null
    private var title: String? = ""
    private var message: String? = ""
    private var titleBtn: String? = ""

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        _viewBinding = DialogAlertBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)

        tvTitle.text = title
        tvContent.text = message
        if (!titleBtn.isNullOrEmpty()) {
            btnPositive.text = titleBtn
        }
        if (!title.isNullOrEmpty()) {
            tvTitle.visibility = View.GONE
        }

        viewBinding.btnPositive.setOnClickListener {
            dismiss(); listener?.onPositiveClicked()
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
