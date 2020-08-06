package com.nl.ktlint_custom_rules

import com.pinterest.ktlint.core.RuleSet
import com.pinterest.ktlint.core.RuleSetProvider

class CustomRuleSetProvider : RuleSetProvider {
    override fun get() = RuleSet(
        "ktlint-custom-rules",
        NoElseIfConditionRule(),
        NoSingleNullableCheckRule(),
        NoVarCompanionRule(),
        NoExclExclRule()
    )
}