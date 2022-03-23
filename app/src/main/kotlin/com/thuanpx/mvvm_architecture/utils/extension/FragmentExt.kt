package com.thuanpx.mvvm_architecture.utils.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.thuanpx.mvvm_architecture.R
import com.thuanpx.mvvm_architecture.utils.navigation.NavAnimateType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Copyright © 2021 Neolab VN.
 * Created by ThuanPx on 15/09/2021.
 */

/**
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.*
 * Source: https://medium.com/androiddevelopers/repeatonlifecycle-api-design-story-8670d1a7d333
 */
inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}

fun Fragment.addOrReplaceFragment(
    @IdRes containerId: Int,
    fragmentManager: FragmentManager? = parentFragmentManager,
    fragment: Fragment,
    isAddFrag: Boolean,
    addToBackStack: Boolean = true,
    animateType: NavAnimateType,
    tag: String = fragment::class.java.simpleName
) {
    fragmentManager?.transact {
        setAnimations(animateType = animateType)

        if (addToBackStack) {
            addToBackStack(tag)
        }

        if (isAddFrag) {
            add(containerId, fragment, tag)
        } else {
            replace(containerId, fragment, tag)
        }
    }
}

fun Fragment.popBackFragment(): Boolean {
    with(parentFragmentManager) {
        val isShowPreviousPage = this.backStackEntryCount > 0
        if (isShowPreviousPage) {
            this.popBackStackImmediate()
        }
        return isShowPreviousPage
    }
}

fun Fragment.generateTag(): String {
    return this::class.java.simpleName
}

fun FragmentManager.isExitFragment(tag: String): Boolean {
    return this.findFragmentByTag(tag) != null
}

fun FragmentTransaction.setAnimations(animateType: NavAnimateType) {
    when (animateType) {
        NavAnimateType.FADE -> {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
        NavAnimateType.SLIDE_DOWN -> {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
        NavAnimateType.SLIDE_UP -> {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
        NavAnimateType.SLIDE_LEFT -> {
            setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, 0, 0)
        }
        NavAnimateType.SLIDE_RIGHT -> {
            setCustomAnimations(R.anim.slide_in_right, 0, 0, R.anim.slide_out_right)
        }
        else -> {
        }
    }
}

/**
 * Runs a FragmentTransaction, then calls commitAllowingStateLoss().
 */
inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}