package com.thuanpx.view_mvvm_architecture.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.thuanpx.view_mvvm_architecture.base.BaseActivity
import com.thuanpx.view_mvvm_architecture.base.viewmodel.BaseViewModel
import com.thuanpx.view_mvvm_architecture.feature.MainActivity
import com.thuanpx.view_mvvm_architecture.widget.ProgressDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

/**
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
    Fragment() {

    protected val viewModel by createViewModelLazy(viewModelClass, { viewModelStore })
    private var _viewBinding: viewBinding? = null
    protected val viewBinding get() = _viewBinding!! // ktlint-disable

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): viewBinding

    protected var progressDialog: ProgressDialog? = null

    fun getMainActivity(): MainActivity? = activity as? MainActivity

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

    /**
     * Fragments outlive their views. Make sure you clean up any references to
     * the binding class instance in the fragment's onDestroyView() method.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    fun showLoading(isShow: Boolean) {
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect { action(it) }
            }
        }
    }
}

