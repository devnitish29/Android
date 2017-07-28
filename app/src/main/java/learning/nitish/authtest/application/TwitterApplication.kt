/*
 * Created by Nitish Singh on 29/7/17 4:42 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:33 AM
 */

package learning.nitish.authtest.application

import android.app.Application
import android.util.Log

import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */


/**
 * Application class to initialse application values like twitter
 *
 */
class TwitterApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig("FA0ZF0yUceZZ3wy8ZtsvqmtTu", "slGjDDQgJSYWbBfvFx0wKmdUhzwPiJWyHi6T51zi2l1y9g2V5l"))
                .debug(true)
                .build()
        Twitter.initialize(config)
    }
}
