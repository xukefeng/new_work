package com.mingzheng.common.request

import com.mingzheng.common.request.common.HttpCommonInterceptor
import com.mingzheng.common.request.common.RetryInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

abstract class RetrofitServiceManager() {
    var mRetrofit: Retrofit? = null
    companion object {
        private const val CONNECTION_TIMEOUT = 50
        private const val READ_TIMEOUT = 200
        private const val WRITE_TIMEOUT = 100
    }

    init {
        val okHttpClientBuild = OkHttpClient.Builder()
        okHttpClientBuild.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(HttpCommonInterceptor())//拦截器添加公共请求参数
            .addInterceptor(RetryInterceptor(2))//重试三次的拦截

        //初始化Retrofit
        mRetrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClientBuild.build())
            .build()
    }

    abstract fun getBaseUrl(): String

    /**
     * 生成对应接口的实例
     *
     * @param service
     * @param <T>
     * @return
    </T> */
    fun <T> create(service: Class<T>): T {
        return mRetrofit!!.create(service)

    }

    class TrustAllCerts : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

        override fun getAcceptedIssuers(): Array<X509Certificate?> {

            return arrayOfNulls<X509Certificate>(0)
        }
    }

    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null

        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(TrustAllCerts()), SecureRandom())

            ssfFactory = sc.socketFactory
        } catch (e: Exception) {
        }

        return ssfFactory
    }
}