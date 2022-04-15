package com.thuanpx.ktext.context

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.thuanpx.ktext.AnimationType
import com.thuanpx.ktext.FADE
import com.thuanpx.ktext.R
import com.thuanpx.ktext.SLIDE_DOWN
import com.thuanpx.ktext.SLIDE_LEFT
import com.thuanpx.ktext.SLIDE_RIGHT
import com.thuanpx.ktext.SLIDE_UP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by ThuanPx on 3/15/20.
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
        viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
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
    @AnimationType animateType: Int,
    tag: String = fragment::class.java.simpleName
) {
    fragmentManager?.transact(animateType) {
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

fun Fragment.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.simpleName,
    @AnimationType animateType: Int = SLIDE_LEFT
) {
    childFragmentManager.transact(animateType) {
        if (addToBackStack) {
            addToBackStack(tag)
        }
        replace(containerId, fragment, tag)
    }
}

fun Fragment.addFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = true,
    tag: String = fragment::class.java.simpleName,
    @AnimationType animateType: Int = SLIDE_LEFT
) {
    childFragmentManager.transact(animateType) {
        if (addToBackStack) {
            addToBackStack(tag)
        }
        add(containerId, fragment, tag)
    }
}

fun Fragment.generateTag(): String {
    return this::class.java.simpleName
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

fun FragmentManager.isExitFragment(tag: String): Boolean {
    return this.findFragmentByTag(tag) != null
}

fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply { arguments = Bundle().apply(argsBuilder) }

/**
 * Runs a FragmentTransaction, then calls commitAllowingStateLoss().
 */
inline fun FragmentManager.transact(
    @AnimationType animateType: Int = SLIDE_LEFT,
    action: FragmentTransaction.() -> Unit,
) {
    beginTransaction().apply {
        setAnimations(animateType)
        action()
    }.commitAllowingStateLoss()
}

fun FragmentTransaction.setAnimations(@AnimationType animateType: Int) {
    when (animateType) {
        FADE -> {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
        SLIDE_DOWN -> {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
        SLIDE_UP -> {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
        SLIDE_LEFT -> {
            setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, 0, 0)
        }
        SLIDE_RIGHT -> {
            setCustomAnimations(R.anim.slide_in_right, 0, 0, R.anim.slide_out_right)
        }
        else -> {
        }
    }
}
