package com.dudu.wristzhixue.util

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedReader
import java.io.InputStream
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


class NetworkUtil {
    companion object{
        operator fun get(url: String): Response {
            val client: OkHttpClient =
                OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).hostnameVerifier(RxUtils.TrustAllHostnameVerifier()).sslSocketFactory(RxUtils.createSSLSocketFactory(), TrustAllCerts()).retryOnConnectionFailure(true).build()
            var requestBuilder: Request.Builder = Request.Builder().url(url).get()
            val request = requestBuilder.build()
            return client.newCall(request).execute()

        }
        class TrustAllCerts : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        }
    }

}