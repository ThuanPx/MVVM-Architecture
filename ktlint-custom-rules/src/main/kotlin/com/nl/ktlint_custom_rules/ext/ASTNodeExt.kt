package com.nl.ktlint_custom_rules.ext

import com.pinterest.ktlint.core.ast.ElementType
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet


fun ASTNode.containBinaryExpressionChild() = getBinaryExpressionChild().isNotEmpty()

fun ASTNode.getBinaryExpressionChild() : List<ASTNode> = this.getChildren(TokenSet.create(ElementType.BINARY_EXPRESSION)).toList()

/**
 * Check if current node is a Binary expression which contains null comparison
 * @return Boolean
 */
fun ASTNode.isNullableComparison() : Boolean {
    if (elementType != ElementType.BINARY_EXPRESSION) return false
    val compareValEle = findChildByType(ElementType.NULL)
    compareValEle?.let {
        return true
    } ?: kotlin.run { return false }
}

/**
 * Get all null comparison as child of the node
 * @return List of BINARY_EXPRESSION node
 */
fun ASTNode.getNullableComparisonChild() : List<ASTNode> {
    val nullableComparisonChild = mutableListOf<ASTNode>()

    // Get all binary expression in node
    val binaryExpressionChild = getBinaryExpressionChild()

    // Loop through the binary expression list and check if it is null comparison
    for (expression in binaryExpressionChild) {
        // is expression null-comparison ?
        if (expression.isNullableComparison())
            nullableComparisonChild.add(expression)
    }

    return nullableComparisonChild
}