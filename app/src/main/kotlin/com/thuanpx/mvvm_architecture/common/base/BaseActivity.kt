package com.thuanpx.mvvm_architecture.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.thuanpx.mvvm_architecture.widget.dialogManager.DialogAlert
import com.thuanpx.mvvm_architecture.widget.dialogManager.DialogConfirm
import com.thuanpx.mvvm_architecture.widget.dialogManager.DialogManager
import com.thuanpx.mvvm_architecture.widget.dialogManager.DialogManagerImpl
import kotlin.reflect.KClass

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 8/5/20.
 *
 * @viewModel -> view model
 * @viewModelClass -> class view model
 * @viewBinding -> class binding
 * @initialize -> init UI, adapter, listener...
 * @onSubscribeObserver -> subscribe observer live data
 *
 */

abstract class BaseActivity<viewModel : BaseViewModel, viewBinding : ViewBinding>(viewModelClass: KClass<viewModel>) :
    AppCompatActivity(), BaseView {

    private val viewModel by ViewModelLazy(
        viewModelClass,
        { viewModelStore },
        { defaultViewModelProviderFactory })
    protected lateinit var viewBinding: viewBinding
    abstract fun inflateViewBinding(inflater: LayoutInflater): viewBinding

    protected abstract fun initialize()

    private lateinit var dialogManager: DialogManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = inflateViewBinding(layoutInflater)
        dialogManager = DialogManagerImpl(this)
        setContentView(viewBinding.root)
        initialize()
        onSubscribeObserver()
    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) showLoading() else hideLoading()
    }

    override fun showLoading() {
        dialogManager.showLoading()
    }

    override fun hideLoading() {
        dialogManager.hideLoading()
    }

    override fun showAlertDialog(
        title: String,
        message: String,
        titleButton: String,
        listener: DialogAlert.Companion.OnButtonClickedListener?
    ) {
        dialogManager.showAlertDialog(title, message, titleButton, listener)
    }

    override fun showConfirmDialog(
        title: String?,
        message: String?,
        titleButtonPositive: String,
        titleButtonNegative: String,
        listener: DialogConfirm.OnButtonClickedListener?
    ) {
        dialogManager.showConfirmDialog(
            title, message, titleButtonPositive, titleButtonNegative, listener
        )
    }

    open fun onSubscribeObserver() {
        viewModel.run {
            isLoading.observe(this@BaseActivity, Observer {
                showLoading(it)
            })
            errorMessage.observe(this@BaseActivity, Observer {
                showAlertDialog(message = it)
            })
            reLogin.observe(this@BaseActivity, Observer {
                // TODO reLogin
            })
        }
    }
}
