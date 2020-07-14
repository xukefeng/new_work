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
import javax.net.ssl.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.security.KeyStore
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.*


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
            .sslSocketFactory(createSSLSocketFactory()!!,getTrustManagerFactory()!!)
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

    private fun getTrustManagerFactory(): X509TrustManager? {
    var trustManagerFactory=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers = trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || (trustManagers[0] is X509TrustManager))) { ("Unexpected default trust managers:"
            + Arrays.toString(trustManagers)) }

         var trustManager= trustManagers[0] as X509TrustManager;
        return trustManager
    }

    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null

        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(getTrustManagerFactory()!!),null)

            ssfFactory = sc.socketFactory
        } catch (e: Exception) {
        }

        return ssfFactory
    }
}