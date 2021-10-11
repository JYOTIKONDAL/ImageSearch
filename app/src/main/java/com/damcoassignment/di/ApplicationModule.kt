package com.damcoassignment.di

import com.damcoassignment.BuildConfig
import com.damcoassignment.ImageSearchApplication
import com.damcoassignment.api.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun getApplicationContext() = ImageSearchApplication()

    @Provides
    @Singleton
    fun provideRetrofit(
        BASE_URL: String
    ): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl(BASE_URL).client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    @Singleton
    fun getUnsafeOkHttpClient(): OkHttpClient.Builder = try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) = Unit

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) = Unit

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )
        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(
            sslSocketFactory,
            trustAllCerts[0] as X509TrustManager
        )
        builder.hostnameVerifier { _, _ -> true }
        builder.readTimeout(10, TimeUnit.MINUTES).connectTimeout(10, TimeUnit.MINUTES)
        builder.addInterceptor(networkInterceptor())
        builder
    } catch (e: Exception) {
        throw RuntimeException(e)
    }


    private fun networkInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}