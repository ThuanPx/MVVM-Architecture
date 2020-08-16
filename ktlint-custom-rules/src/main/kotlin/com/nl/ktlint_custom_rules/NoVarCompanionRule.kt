package com.nl.ktlint_custom_rules

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.COLON
import org.jetbrains.kotlin.KtNodeTypes
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes

/**
 * Copyright © 2020 Neolab VN.
 * Created by ThuanPx on 7/12/20.
 */
class NoVarCompanionRule : Rule("no-var-companion") {
    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
    ) {
        if (node.elementType == KtStubElementTypes.OBJECT_DECLARATION) {
            if (node.findChildByType(COLON) != null) return
            val nodeChildes = node.findChildByType(KtNodeTypes.CLASS_BODY)
                ?.getChildren(TokenSet.create(KtNodeTypes.PROPERTY))
            nodeChildes?.forEach { child ->
                val childTextSplit = child.text.split(" ")
                if (childTextSplit.contains("var")) {
                    emit(node.startOffset, "Not used var in companion object!", false)
                }
            }
        }
    }
}