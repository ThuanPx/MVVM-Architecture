package com.nl.ktlint_custom_rules

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.EXCLEXCL
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * Created by ThuanPx on 7/17/20.
 */

class NoExclExclRule : Rule("no-excl-excl") {
    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (node.elementType == EXCLEXCL) {
            emit(node.startOffset, "Not used !! ", false)
        }
    }
}