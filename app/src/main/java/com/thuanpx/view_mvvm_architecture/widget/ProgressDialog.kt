package com.thuanpx.view_mvvm_architecture.widget

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.thuanpx.view_mvvm_architecture.R

@Suppress("DEPRECATION")
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
