/*
 * Created by Nitish Singh on 29/7/17 4:41 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:33 AM
 */

package learning.nitish.authtest.api

import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import learning.nitish.authtest.BuildConfig
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */

/**
 * An Interceptor class used in OKHTTP and Retrofit for OAuth
 */

//TODO: Need to Check the root cause of Oauth failure
class RetrofitInterceptor : Interceptor {
    private var token: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (token == null) {
            val body = chain.proceed(getToken()).body()

            val gson = Gson()
            val jsonString = gson.toJson(body)
            try {
                val jsonObject = JSONObject(jsonString)
                token = "Bearer " + jsonObject.optString("access_token")
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.d(RetrofitInterceptor::class.java.name, "Error fetching token")
            }

        }

        request = request.newBuilder()
                .addHeader("Authorization", token!!)
                .build()

        return chain.proceed(request)
    }

    private fun getToken(): Request {
        val bearerToken = BuildConfig.CONSUMER_KEY +
                ":" + BuildConfig.CONSUMER_SECRET

        val base64BearerToken = "Basic " + Base64.encodeToString(bearerToken.toByteArray(), Base64.NO_WRAP)
        val requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), "grant_type=client_credentials")

        return Request.Builder()
                .url(BuildConfig.AUTH_END_POINT)
                .post(requestBody)
                .header("Authorization", base64BearerToken)
                .header("Content-Encoding", "gzip")
                .header("User-Agent", "My Twitter App v1.0.23")
                .header("Content-type", "application/x-www-form-urlencoded;charset=UTF-8")
                .build()
    }


}
