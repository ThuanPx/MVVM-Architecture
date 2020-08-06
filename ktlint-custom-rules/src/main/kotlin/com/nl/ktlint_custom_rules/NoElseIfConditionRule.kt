package com.nl.ktlint_custom_rules

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.BINARY_EXPRESSION
import com.pinterest.ktlint.core.ast.ElementType.EQEQ
import com.pinterest.ktlint.core.ast.ElementType.EXCLEQ
import com.pinterest.ktlint.core.ast.ElementType.NULL
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.KtNodeTypes.ELSE
import org.jetbrains.kotlin.KtNodeTypes.OPERATION_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

class NoElseIfConditionRule : Rule("no-else-if-condition") {
    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if(node.elementType == KtNodeTypes.IF) {
            node.findChildByType(ELSE)?.let {
                if (it.firstChildNode.elementType == KtNodeTypes.IF) {
                    emit(node.firstChildNode.startOffset, "Please use syntax WHEN to replace ELSE IF", false)
                }
            }
        }
    }
}