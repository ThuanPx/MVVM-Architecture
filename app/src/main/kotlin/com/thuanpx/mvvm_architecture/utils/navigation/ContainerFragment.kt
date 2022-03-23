package com.thuanpx.mvvm_architecture.utils.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.thuanpx.ktext.AnimationType
import com.thuanpx.ktext.context.addOrReplaceFragment
import com.thuanpx.mvvm_architecture.R
import timber.log.Timber

@Suppress("DEPRECATION")
class ContainerFragment : Fragment() {

    private var currentTab = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentTab = arguments?.getInt(EXTRA_TAB) ?: return
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @NonNull container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isAdded) {
            return
        }
        val fragment = childFragmentManager.findFragmentById(containerViewId) ?: return
        fragment.userVisibleHint = isVisibleToUser
    }

    fun popBack(isToRoot: Boolean) {
        val fragManager = childFragmentManager
        val backStackCount = fragManager.backStackEntryCount
        if (isToRoot) {
            for (i in 0 until backStackCount) {
                val backStackId = fragManager.getBackStackEntryAt(i).id
                fragManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        } else {
            val isShowPrevious = backStackCount > 1
            if (isShowPrevious) {
                fragManager.popBackStackImmediate()
            }
        }
    }

    fun isCanPopBack(): Boolean {
        with(childFragmentManager) {
            val isShowPrevious = this.backStackEntryCount > 1
            if (isShowPrevious) {
                this.popBackStackImmediate()
            }
            return isShowPrevious
        }
    }

    fun addOrReplaceFragment(
        fragment: Fragment,
        isAddFrag: Boolean,
        addToBackStack: Boolean,
        @AnimationType animateType: Int,
        tag: String
    ) {

        with(childFragmentManager) {
            val isExitsFragment = findFragmentByTag(tag)
            Timber.d("$tag, isExits = $isExitsFragment, isAddFrag = $isAddFrag")
            if (isExitsFragment != null) return

            addOrReplaceFragment(
                containerId = containerViewId,
                fragmentManager = this,
                fragment = fragment,
                isAddFrag = isAddFrag,
                addToBackStack = addToBackStack,
                animateType = animateType,
                tag = tag
            )
        }
    }

    companion object {

        private const val TAG = "ContainerFragment"

        private const val EXTRA_TAB = "EXTRA_TAB"

        const val containerViewId = R.id.layoutContainer

        fun newInstance(tab: Int) = ContainerFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_TAB, tab)
            }
        }
    }
}
