/*
 * Created by Nitish Singh on 29/7/17 4:41 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:17 AM
 */

package learning.nitish.authtest.api

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */


/**
 * OKHTTP client builder class used in retrofit
 */
class RestClient private constructor() {


    init {
        mClient = OkHttpClient()
    }

    companion object {


        lateinit var mClient: OkHttpClient

        fun getClientInstance(context: Context): OkHttpClient {
            val cacheSize = 30 * 1024 * 1024 //30MB


            //creating request Interceptor
            val requestInterceptor = Interceptor { chain ->
                val original = chain.request()
                val orginalUrl = original.url()
                val url = orginalUrl.newBuilder()
                        .build()
                val newRequest: Request
                newRequest = original.newBuilder()
                        .url(url)
                        .header("Accept", "application/json")
                        .build()
                chain.proceed(newRequest)
            }

            //creating log Interceptor
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY


            if (mClient == null) {
                synchronized(RestClient::class.java) {
                    if (mClient == null) {
                        mClient = OkHttpClient().newBuilder()
                                .cache(Cache(context.applicationContext.cacheDir, cacheSize.toLong()))
                                .readTimeout(60, TimeUnit.SECONDS)
                                .connectTimeout(60, TimeUnit.SECONDS)

                                .addInterceptor(logging)
                                .addInterceptor(RetrofitInterceptor())
                                .addInterceptor(requestInterceptor)
                                .build()


                    }
                }
            }
            return mClient
        }
    }

}
