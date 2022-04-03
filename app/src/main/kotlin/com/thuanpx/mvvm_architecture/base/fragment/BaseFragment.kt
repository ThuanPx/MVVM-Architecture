package com.thuanpx.mvvm_architecture.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.viewbinding.ViewBinding
import com.thuanpx.mvvm_architecture.base.BaseActivity
import com.thuanpx.mvvm_architecture.base.BaseView
import com.thuanpx.mvvm_architecture.base.viewmodel.BaseViewModel
import com.thuanpx.mvvm_architecture.widget.dialogManager.DialogAlert
import com.thuanpx.mvvm_architecture.widget.dialogManager.DialogConfirm
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

abstract class BaseFragment<viewModel : BaseViewModel, viewBinding : ViewBinding>(viewModelClass: KClass<viewModel>) :
    Fragment(), BaseView {

    protected val viewModel by createViewModelLazy(viewModelClass, { viewModelStore })
    private var _viewBinding: viewBinding? = null
    protected val viewBinding get() = _viewBinding!! // ktlint-disable

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): viewBinding

    protected abstract fun initialize()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = inflateViewBinding(inflater, container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        onSubscribeObserver()
    }

    override fun showLoading(isShow: Boolean) {
        if (isShow) showLoading() else hideLoading()
    }

    override fun showLoading() {
        (activity as? BaseActivity<*, *>)?.showLoading()
    }

    override fun hideLoading() {
        (activity as? BaseActivity<*, *>)?.hideLoading()
    }

    override fun showAlertDialog(
        title: String,
        message: String,
        titleButton: String,
        listener: DialogAlert.Companion.OnButtonClickedListener?
    ) {
        (activity as? BaseActivity<*, *>)?.showAlertDialog(title, message, titleButton, listener)
    }

    override fun showConfirmDialog(
        title: String?,
        message: String?,
        titleButtonPositive: String,
        titleButtonNegative: String,
        listener: DialogConfirm.OnButtonClickedListener?
    ) {
        (activity as? BaseActivity<*, *>)?.showConfirmDialog(
            title, message, titleButtonPositive, titleButtonNegative, listener
        )
    }

    /**
     * Fragments outlive their views. Make sure you clean up any references to
     * the binding class instance in the fragment's onDestroyView() method.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    open fun onSubscribeObserver() {
        viewModel.run {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            exception.observe(viewLifecycleOwner) {
                (activity as? BaseActivity<*, *>)?.handleApiError(it)
            }
        }
    }
}
