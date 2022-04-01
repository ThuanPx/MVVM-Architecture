package com.thuanpx.mvvm_architecture.data.remote.api.middleware

import androidx.annotation.NonNull
import com.thuanpx.mvvm_architecture.data.local.datastore.PreferenceDataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@DelicateCoroutinesApi
class InterceptorImpl @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {

        val builder = initializeHeader(chain)
        val request = builder.build()

        return chain.proceed(request)
    }

    private fun initializeHeader(chain: Interceptor.Chain): Request.Builder {
        val originRequest = chain.request()
        val token = runBlocking { preferenceDataStore.token.first() }

        return originRequest.newBuilder()
            .header("Accept", "application/json")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Cache-Control", "no-store")
             .addHeader(KEY_TOKEN, TOKEN_TYPE + token)
            .method(originRequest.method, originRequest.body)
    }

    companion object {
        private const val TOKEN_TYPE = "Bearer "
        private const val KEY_TOKEN = "Authorization"
    }
}
