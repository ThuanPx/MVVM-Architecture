package com.thuanpx.mvvm_architecture.utils.navigation

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.thuanpx.ktext.AnimationType
import com.thuanpx.ktext.NONE
import com.thuanpx.ktext.context.generateTag
import com.thuanpx.ktext.context.transact
import com.thuanpx.ktext.number.nullToZero

/**
 * Created by ThuanPx on 3/13/22.
 */

@DslMarker
annotation class NavigationManagerDsl

@MainThread
@NavigationManagerDsl
inline fun FragmentActivity.createNavigationManager(
    block: BottomNavigationManager.Builder.() -> Unit
): BottomNavigationManager {
    return BottomNavigationManager.Builder().apply(block).build()
}

class BottomNavigationManager(val builder: Builder) {

    data class Builder(
        var tabs: List<Int>? = null,
        var mainFragmentManager: FragmentManager? = null,
        var mainContainerViewId: Int? = null
    ) {
        fun build(): BottomNavigationManager = BottomNavigationManager(builder = this)
    }

    private var tabs: List<Int>?
    private var mainFragmentManager: FragmentManager?
    private var mainContainerViewId: Int?
    private var fragmentsRoot: MutableList<ContainerFragment> = mutableListOf()

    private var currentTab = -1
    private var currentFragmentRoot: ContainerFragment
    private val handler = Handler(Looper.getMainLooper())

    init {
        builder.apply {
            this@BottomNavigationManager.tabs = tabs
            this@BottomNavigationManager.mainFragmentManager = mainFragmentManager
            this@BottomNavigationManager.mainContainerViewId = mainContainerViewId
        }
        if (tabs == null) {
            throw IllegalArgumentException("tabs is null")
        }
        if (tabs?.size.nullToZero() <= 1) {
            throw IllegalArgumentException("Number of tabs cannot be less than 1")
        }
        if (mainFragmentManager == null) {
            throw IllegalArgumentException("mainFraManager is null")
        }
        if (mainContainerViewId == null) {
            throw IllegalArgumentException("mainContainerViewId is null")
        }

        mainFragmentManager?.transact {

            tabs?.forEachIndexed { index, tab ->
                val fragment = ContainerFragment.newInstance(tab = tab)
                fragmentsRoot.add(index, fragment)

                val tag = fragment.generateTag()
                addToBackStack(tag)
                add(mainContainerViewId!!, fragment, tag)
            }
        }

        currentTab = tabs!!.first()
        currentFragmentRoot = fragmentsRoot.first()
    }

    fun addOrReplaceFragment(
        fragment: Fragment,
        isAddFrag: Boolean = false,
        addToBackStack: Boolean = true,
        @AnimationType animateType: Int = NONE,
        tag: String = fragment::class.java.simpleName
    ) {
        handler.post {
            currentFragmentRoot.addOrReplaceFragment(
                fragment = fragment,
                isAddFrag = isAddFrag,
                addToBackStack = addToBackStack,
                animateType = animateType,
                tag = tag
            )
        }
    }

    fun switchTab(
        tab: Int,
        fragment: Fragment,
        isAddFrag: Boolean = false,
        addToBackStack: Boolean = true,
        @AnimationType animateType: Int = NONE,
        tag: String = fragment::class.java.simpleName
    ) {
        if (currentTab == tab) return

        val switchFragRoot = getFragmentRoot(tab = tab) ?: return

        mainFragmentManager?.transact(animateType) {
            hide(currentFragmentRoot).show(switchFragRoot)
        }

        switchFragRoot.addOrReplaceFragment(
            fragment = fragment,
            isAddFrag = isAddFrag,
            addToBackStack = addToBackStack,
            animateType = animateType,
            tag = tag
        )

        currentTab = tab
        currentFragmentRoot = switchFragRoot
    }

    fun getFragment(position: Int): Fragment? {
        return try {
            fragmentsRoot[position]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    fun getCurrentFragment(): Fragment = currentFragmentRoot

    fun getCurrentTab(): Int = currentTab

    fun popBack(isToRoot: Boolean) = currentFragmentRoot.popBack(isToRoot = isToRoot)

    fun isCanPopBack(): Boolean {
        return currentFragmentRoot.isCanPopBack()
    }

    private fun getFragmentRoot(tab: Int): ContainerFragment? {
        tabs?.forEachIndexed { index, tabRoot ->
            if (tab == tabRoot) {
                return fragmentsRoot[index]
            }
        }
        return null
    }
}
