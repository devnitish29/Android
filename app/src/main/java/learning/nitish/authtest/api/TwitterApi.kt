/*
 * Created by Nitish Singh on 29/7/17 4:42 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:33 AM
 */

package learning.nitish.authtest.api

import com.twitter.sdk.android.core.models.Tweet

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */


/**
 * Retrofit Service interface used in network operations
 */

interface TwitterApi {


    /**
     * Retrofit Api Method to get userTimeLine tweets using $user_id, $user_name , $count.
     */
    @GET("statuses/user_timeline.json")
    fun getUserTimeLines(@Query("screen_name") screen_name: String,
                         @Query("user_id") user_id: Long,
                         @Query("count") count: Int): Observable<List<Tweet>>
}
