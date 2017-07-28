/*
 * Created by Nitish Singh on 29/7/17 4:41 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:17 AM
 */

package learning.nitish.authtest.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.twitter.sdk.android.core.models.Tweet

import de.hdodenhof.circleimageview.CircleImageView
import learning.nitish.authtest.R
import learning.nitish.authtest.utlis.Constants
import learning.nitish.authtest.utlis.DateUtils

/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */

/**
 * An Adapter class for user timeline tweets supporting the recyclerviewer
 */
class TwitterAdapter(private val mContext: Context, private val tweets: List<Tweet>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        val v1 = inflater.inflate(R.layout.item_tweet_layout, parent, false)
        viewHolder = TweetViewHolder(v1)
        return viewHolder


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var tweet = tweets!![position]

        val retweet = tweet.retweetedStatus

        when (getItemViewType(position)) {
            TYPE_ITEM -> {
                val tweetViewHolder = holder as TweetViewHolder
                tweetViewHolder.mTxtDate.text = DateUtils.getTwitterRelativeTime(tweet.createdAt)
                if (retweet != null) {
                    tweetViewHolder.mTxtRetweetedMsg.visibility = View.VISIBLE
                    val text = mContext.getString(R.string.retweeted_msg, tweet.user.name)
                    tweetViewHolder.mTxtRetweetedMsg.text = text
                    tweet = retweet
                } else {
                    tweetViewHolder.mTxtRetweetedMsg.visibility = View.GONE
                }

                tweetViewHolder.mTxtTweet.text = Html.fromHtml(tweet.text)
                Linkify.addLinks(tweetViewHolder.mTxtTweet, Linkify.ALL)

                if (tweet.entities != null) {
                    val mediaEntities = tweet.entities.media
                    if (mediaEntities != null && !mediaEntities.isEmpty()) {
                        tweetViewHolder.mImgTweetMedia.visibility = View.VISIBLE
                        val media = mediaEntities[0]
                        Glide.with(mContext).load(media.mediaUrl).into(tweetViewHolder.mImgTweetMedia)
                    } else {
                        tweetViewHolder.mImgTweetMedia.visibility = View.GONE
                    }
                }

                if (tweet.retweetCount > 0) {
                    tweetViewHolder.mTxtRetweet.text = tweet.retweetCount.toString()
                } else {
                    tweetViewHolder.mTxtRetweet.text = ""
                }

                if (tweet.favoriteCount > 0) {
                    tweetViewHolder.mTxtFavorite.text = tweet.favoriteCount.toString()
                } else {
                    tweetViewHolder.mTxtFavorite.text = ""
                }


                val user = tweet.user
                Glide.with(mContext).load(user.profileImageUrl).into(tweetViewHolder.mImgUser)
                tweetViewHolder.mTxtUserScreenName.text = Constants.PREFIX_SCREEN_NAME + user.screenName
                tweetViewHolder.mTxtUserName.text = user.name
            }
            TYPE_FOOTER -> {
            }
        }//


    }


   //Gets the individual type of views depending on postion in the list
    override fun getItemViewType(position: Int): Int {

        return TYPE_ITEM
    }


    //Gets the total count of elements present in the listview
    override fun getItemCount(): Int {
        return tweets?.size ?: 0
    }


    //Viewholder class used by the adapter, contains all the view elemnts in the layout
    inner class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var mTxtRetweetedMsg: TextView
        internal var mImgUser: CircleImageView
        internal var mTxtUserScreenName: TextView
        internal var mTxtUserName: TextView
        internal var mTxtDate: TextView
        internal var mTxtTweet: TextView
        internal var mImgTweetMedia: ImageView
        internal var mTxtRetweet: TextView
        internal var mTxtFavorite: TextView


        init {

            mTxtRetweetedMsg = itemView.findViewById<TextView>(R.id.txt_retweeted_msg)
            mImgUser = itemView.findViewById<CircleImageView>(R.id.img_user)
            mTxtUserScreenName = itemView.findViewById<TextView>(R.id.txt_user_screen_name)
            mTxtUserName = itemView.findViewById<TextView>(R.id.txt_user_name)
            mTxtDate = itemView.findViewById<TextView>(R.id.txt_date)
            mTxtTweet = itemView.findViewById<TextView>(R.id.txt_tweet)
            mImgTweetMedia = itemView.findViewById<ImageView>(R.id.img_tweet_media)
            mTxtRetweet = itemView.findViewById<TextView>(R.id.txt_retweet)
            mTxtFavorite = itemView.findViewById<TextView>(R.id.txt_favorite)
        }
    }

    companion object {

        private val TYPE_ITEM = 1
        private val TYPE_FOOTER = 2
    }


}
