package com.thuanpx.mvvm_architecture.data.remote.api.middleware

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class BooleanAdapter : TypeAdapter<Boolean>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Boolean?) {
        value?.let {
            out.nullValue()
            return
        }
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Boolean? {
        return when (`in`.peek()) {
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            JsonToken.BOOLEAN -> `in`.nextBoolean()
            JsonToken.NUMBER -> `in`.nextInt() != 0
            JsonToken.STRING -> java.lang.Boolean.valueOf(`in`.nextString())
            else -> null
        }
    }
}
