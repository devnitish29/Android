/*
 * Created by Nitish Singh on 29/7/17 4:42 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:33 AM
 */

package learning.nitish.authtest.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.services.StatusesService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import learning.nitish.authtest.R
import learning.nitish.authtest.adapter.TwitterAdapter
import learning.nitish.authtest.api.TwitterApi
import learning.nitish.authtest.utlis.ItemOffsetDecoration
import java.util.*

/**
 * A simple Fragment class to load user timelines using pagination and recyclerviewer
 */
class RxJavaFragment : Fragment() {
    lateinit var tweetsListView: RecyclerView
    lateinit var twitterAdapter: TwitterAdapter
    internal var tweetList: MutableList<Tweet> = ArrayList()
    lateinit var progressBar: ProgressBar


    private val ITEM_COUNT = 20
    private var SINCE_ID: Long = 0
    private var isLoading = false
    private var isLastPage = false
    private var USER_ID: Long = 0
    private var USER_NAME = ""


    lateinit var twitterApiClient: TwitterApiClient
    lateinit var statusesService: StatusesService


    lateinit var twitterApi: TwitterApi


    private var mCompositeDisposable: CompositeDisposable? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_rx_java, container, false)

        mCompositeDisposable = CompositeDisposable()
        //twitterApi = RestClientAdapter.createRestAdapter<TwitterApi>(TwitterApi::class, activity)

        twitterApiClient = TwitterCore.getInstance().apiClient
        statusesService = twitterApiClient.statusesService
        USER_ID = TwitterCore.getInstance().sessionManager.activeSession.userId
        USER_NAME = TwitterCore.getInstance().sessionManager.activeSession.userName
        progressBar = view.findViewById<View>(R.id.main_progress) as ProgressBar
        tweetsListView = view.findViewById<RecyclerView>(R.id.tweetsList)
        val linearLayoutManager = LinearLayoutManager(context)
        tweetsListView.layoutManager = linearLayoutManager
        tweetList.clear()
        twitterAdapter = TwitterAdapter(activity, tweetList)

        tweetsListView.adapter = twitterAdapter


        tweetsListView.itemAnimator = DefaultItemAnimator()
        tweetsListView.addItemDecoration(ItemOffsetDecoration(20))
        val itemDecorator = DividerItemDecoration(activity,
                DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(resources.getDrawable(R.drawable.divider))
        tweetsListView.addItemDecoration(itemDecorator)

        loadFirstPage()

        //   onLoadRxJava();


        /**
         * Pagination implementation on the recyclerviewer using inbuilt onScrollListner
         */
        tweetsListView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val lastvisibleitemposition = linearLayoutManager.findLastVisibleItemPosition()

                if (lastvisibleitemposition == twitterAdapter.itemCount - 1) {

                    if (!isLoading && !isLastPage) {
                        isLoading = true
                        loadNextPage()

                    }


                }
            }
        })

        return view
    }


    /**
     * Method to get userTimeLine tweets using $user_id, $user_name , $count, $since_id.
     * Used for pagination
     */
    private fun loadNextPage() {
        val call = statusesService.userTimeline(USER_ID, USER_NAME, ITEM_COUNT, null, SINCE_ID, false, false, true, true)
        call.enqueue(object : Callback<List<Tweet>>() {
            override fun success(result: Result<List<Tweet>>) {
                if (result.response.isSuccessful) {


                    tweetList.addAll(result.data)
                    twitterAdapter.notifyDataSetChanged()
                    isLoading = false

                    if (SINCE_ID == result.data[result.data.size - 1].getId()) {
                        isLastPage = true
                        Toast.makeText(context, "This is your first Tweet", Toast.LENGTH_SHORT).show()
                    }


                    SINCE_ID = result.data[result.data.size - 1].getId()
                }
            }

            override fun failure(exception: TwitterException) {

                println("exception  :  ${exception.stackTrace}")

            }
        })
    }


    /**
     * Method to get userTimeLine tweets using $user_id, $user_name , $count.
     * Initial tweets are loaded using this method
     */
    private fun loadFirstPage() {


        val call = statusesService.userTimeline(USER_ID, USER_NAME, ITEM_COUNT, null, null, null, false, false, true)


        call.enqueue(object : Callback<List<Tweet>>() {
            override fun success(result: Result<List<Tweet>>) {
                if (result.response.isSuccessful) {
                    progressBar.visibility = View.GONE
                    progressBar.visibility = View.GONE


                    tweetList.clear()
                    tweetList.addAll(result.data)
                    twitterAdapter.notifyDataSetChanged()
                    SINCE_ID = result.data[result.data.size - 1].getId()


                }
            }

            override fun failure(exception: TwitterException) {

                println("exception  :  ${exception.stackTrace}")
            }
        })
    }


    /**
     * RxJava Method to get userTimeLine tweets using $user_id, $user_name , $count, $since_id.
     * Incomplete as Issues in Oauth
     */
    private fun onLoadRxJava() {

        val timelines = twitterApi.getUserTimeLines(USER_NAME, USER_ID, 20)
        timelines.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<List<Tweet>>() {
                    override fun onNext(@NonNull tweetList: List<Tweet>) {

                        //TODO: Add all tweets in adapter used by recyclerview
                    }

                    override fun onError(@NonNull e: Throwable) {
                        //TODO: Override the OnError method to handle any errors
                    }

                    override fun onComplete() {
                        //TODO: User notification on complete of request
                    }
                })


    }


//    companion object {
//
//
//        private val TAG = "NIITSH"
//    }
}// Required empty public constructor
