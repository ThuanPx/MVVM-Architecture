package com.thuanpx.view_mvvm_architecture.base.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.thuanpx.view_mvvm_architecture.base.BaseActivity
import com.thuanpx.view_mvvm_architecture.base.viewmodel.BaseViewModel
import com.thuanpx.view_mvvm_architecture.R
import com.thuanpx.view_mvvm_architecture.widget.ProgressDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

/**
 * Created by ThuanPx on 8/5/20.
 *
 * @viewModel -> name view model
 * @classViewModel -> class view model
 * @viewBinding -> class binding
 * @initialize -> init UI, adapter, listener...
 * @onSubscribeObserver -> subscribe observer
 *
 */

abstract class BaseDialogFragment<viewModel : BaseViewModel, viewBinding : ViewBinding>(viewModelClass: KClass<viewModel>) :
    DialogFragment() {

    protected val viewModel by createViewModelLazy(viewModelClass, { viewModelStore })
    private var _viewBinding: viewBinding? = null
    protected val viewBinding get() = _viewBinding!! // ktlint-disable
    protected var progressDialog: ProgressDialog? = null

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
        progressDialog = ProgressDialog(requireContext())
        initialize()
        onSubscribeObserver()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }

    /**
     * Fragments outlive their views. Make sure you clean up any references to
     * the binding class instance in the fragment's onDestroyView() method.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    private fun showLoading(isShow: Boolean) {
        (activity as? BaseActivity<*, *>)?.showLoading(isShow)
    }

    open fun onSubscribeObserver() {
        viewModel.run {
            isLoading.launchAndCollect {
                showLoading(it)
            }
            error.launchAndCollect {
                (activity as? BaseActivity<*, *>)?.handleApiError(it)
            }
        }
    }

    protected inline infix fun <T> Flow<T>.launchAndCollect(crossinline action: (T) -> Unit) {
        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    collect { action(it) }
                }
            }
        }
    }
}
