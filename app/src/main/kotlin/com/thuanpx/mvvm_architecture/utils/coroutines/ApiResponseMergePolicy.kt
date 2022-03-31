package com.thuanpx.mvvm_architecture.utils.coroutines

/**
 * [ApiResponseMergePolicy] is a policy for merging response data depend on the success or not.
 */
enum class ApiResponseMergePolicy {
    /**
     * Regardless of the order, ignores failure responses in the responses.
     * if there are three responses (success, success, failure) or (success, failure, success),
     * the response will be the [ApiResponse.Success] that has a merged list of the data.
     */
    IGNORE_FAILURE,

    /**
     * Regardless of the order, prefers failure responses in the responses.
     * if there are three responses (success, success, failure) or (success, failure, success),
     * the response will be the [ApiResponse.Failure.Error] or [ApiResponse.Failure.Exception].
     */
    PREFERRED_FAILURE
}
