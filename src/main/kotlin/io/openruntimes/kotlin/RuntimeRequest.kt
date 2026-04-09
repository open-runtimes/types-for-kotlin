package io.openruntimes.kotlin

import com.google.gson.Gson
import com.google.gson.GsonBuilder

data class RuntimeRequest(
    val method: String,
    val scheme: String,
    val host: String,
    val port: Int,
    val path: String,
    val query: MutableMap<String, String>,
    val queryString: String,
    val headers: MutableMap<String, String>,
    val bodyBinary: ByteArray,
    val url: String,
) {
    companion object {
        private val gson: Gson = GsonBuilder().serializeNulls().create()
    }

    val bodyText: String
        get() = String(bodyBinary)
    val bodyJson: MutableMap<String, Any>
        get() = gson.fromJson(bodyText, MutableMap::class.java) as MutableMap<String, Any>
    val bodyRaw: String
        get() = bodyText
    val body: Any
        get() {
            val contentType = headers.getOrDefault("content-type", "text/plain").lowercase()

            if (contentType.startsWith("application/json")) {
                if (bodyBinary.isEmpty()) {
                    val emptyBody: MutableMap<String, Any> = mutableMapOf()
                    return emptyBody
                } else {
                    return bodyJson
                }
            }

            return bodyText
        }
}
