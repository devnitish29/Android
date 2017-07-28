/*
 * Created by Nitish Singh on 29/7/17 4:42 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:33 AM
 */

package learning.nitish.authtest.fragments


import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import learning.nitish.authtest.R

/**
 * A simple ListFragment class to display usertimeline tweets using TwitterUI methods
 */
class TweeterUIFragment : ListFragment() {

    internal var button: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userTimeline = UserTimeline.Builder()
                .screenName("iamNRathore")
                .includeReplies(true)
                .includeRetweets(true)
                .build()
        val adapter = TweetTimelineListAdapter.Builder(activity)
                .setTimeline(userTimeline)
                .build()
        listAdapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_tweeter_ui, container, false)

        return view
    }

}
