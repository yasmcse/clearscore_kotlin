package com.example.yasirnazir.clearscore.networking

import com.example.yasirnazir.clearscore.BuildConfig
import com.example.yasirnazir.clearscore.base_classes.RxThreadCallAdapterFactory

import java.io.File
import java.io.IOException

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Created by yasirnazir on 3/14/18.
 */
@Module
class NetworkModule(internal var cacheFile: File) {

    @Provides
    @Singleton
    internal fun provideCall(): Retrofit {
        var cache: Cache? = null
        try {
            cache = Cache(cacheFile, (10 * 1024 * 1024).toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    // Customize the request
                    val request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                            .build()

                    val response = chain.proceed(request)
                    response.cacheResponse()

                    response
                }
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(cache)

                .build()


        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxThreadCallAdapterFactory())

                .build()
    }

    @Provides
    @Singleton
    fun providesNetworkService(
            retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun providesService(
            networkApi: NetworkApi): NetworkService {
        return NetworkService(networkApi)
    }

}
