/*
 * Created by Nitish Singh on 29/7/17 4:41 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:17 AM
 */

package learning.nitish.authtest.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import learning.nitish.authtest.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */


/**
 * Retrofit builder object for network operations
 */
object RestClientAdapter {


    fun <A> createRestAdapter(AdapterClass: Class<A>, context: Context): A {


        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.serializeNulls().create()

        val restAdapter = Retrofit.Builder()
                .baseUrl(BuildConfig.END_POINT)
                .client(RestClient.getClientInstance(context))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return restAdapter.create(AdapterClass)


    }
}
