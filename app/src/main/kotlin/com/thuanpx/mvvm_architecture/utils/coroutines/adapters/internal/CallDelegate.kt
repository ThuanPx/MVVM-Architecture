package com.thuanpx.mvvm_architecture.utils.coroutines.adapters.internal

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author skydoves (Jaewoong Eum)
 *
 * CallDelegate is a delegate [Call] proxy for handling and transforming one to another generic types
 * between the two different types of [Call] requests.
 */
internal abstract class CallDelegate<TIn, TOut>(
    protected val proxy: Call<TIn>
) : Call<TOut> {
    final override fun enqueue(callback: Callback<TOut>) = enqueueImpl(callback)
    final override fun execute(): Response<TOut> = executeImpl()
    final override fun clone(): Call<TOut> = cloneImpl()

    override fun cancel() = proxy.cancel()
    override fun request(): Request = proxy.request()
    override fun isExecuted() = proxy.isExecuted
    override fun isCanceled() = proxy.isCanceled
    override fun timeout(): Timeout = proxy.timeout()

    abstract fun enqueueImpl(callback: Callback<TOut>)
    abstract fun executeImpl(): Response<TOut>
    abstract fun cloneImpl(): Call<TOut>
}
