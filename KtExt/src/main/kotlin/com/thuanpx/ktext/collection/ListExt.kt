package com.thuanpx.ktext.collection

/**
 * Created by ThuanPx on 3/12/20.
 */

inline fun <reified T> List<T>?.equalsExt(listCompare: MutableList<T>?) = this?.size == listCompare?.size &&
    this?.containsAll(listCompare ?: mutableListOf()) == true
