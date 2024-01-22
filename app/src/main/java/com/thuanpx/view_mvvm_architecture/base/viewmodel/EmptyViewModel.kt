package com.thuanpx.view_mvvm_architecture.base.viewmodel

import com.thuanpx.view_mvvm_architecture.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by ThuanPx on 3/24/21.
 */
@HiltViewModel
class EmptyViewModel @Inject constructor() : BaseViewModel()
