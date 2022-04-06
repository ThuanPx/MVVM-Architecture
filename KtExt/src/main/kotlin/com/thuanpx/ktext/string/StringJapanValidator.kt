@file:JvmName("KtExtStringJapanValidator")
@file:JvmMultifileClass

package com.thuanpx.ktext.string

/**
 * Created by ThuanPx on 24/01/2022.
 */

val String.isAllHalfSize: Boolean
    get() {
        val p = "^[a-zA-Z0-9!@#$%^&*)(+=._-]+\$".toRegex()
        return matches(p)
    }

val String.isKana: Boolean
    get() {
        val p = "[ぁ-んァ-ン]".toRegex()
        return matches(p)
    }

val String.isNumberHalfSize: Boolean
    get() {
        val p = "[0-9]+".toRegex()
        return matches(p)
    }

val String.isKanaFullSize: Boolean
    get() {
        val p = "^[ァ-ン]+|[Ａ-ｚ]+|[０-９]+\$".toRegex()
        return matches(p)
    }

val String.isKanaHalfSize: Boolean
    get() {
        val p = "^[A-Z0-9ァ-ンﾞﾟ゛。「」 ()/\\\\.-]+\$".toRegex()
        return matches(p)
    }

val String.isKanaHalfSizeWithSpaceFullSize: Boolean
    get() {
        val p = "^[A-Z0-9ァ-ンﾞﾟ゛。「」 ()/\\\\.\\s-]+\$".toRegex()
        return matches(p)
    }