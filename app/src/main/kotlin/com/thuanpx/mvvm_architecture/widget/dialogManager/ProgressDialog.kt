package com.thuanpx.mvvm_architecture.widget.dialogManager

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.thuanpx.mvvm_architecture.R

class ProgressDialog(context: Context) : Dialog(context) {

    init {
        initView()
    }

    private fun initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_progress_dialog)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        dismiss()
    }
}
