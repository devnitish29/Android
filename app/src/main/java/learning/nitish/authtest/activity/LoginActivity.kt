/*
 * Created by Nitish Singh on 29/7/17 4:40 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:11 AM
 */

package learning.nitish.authtest.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import learning.nitish.authtest.R

/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */

/**
 * Activity Class for user loing using twitter api and create a session
 */
class LoginActivity : AppCompatActivity() {
    lateinit var loginButton: TwitterLoginButton
    private val TAG = "NITISH"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.login_button) as TwitterLoginButton

        loginButton.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {

                val intent = Intent(this@LoginActivity, HomeScreenActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun failure(exception: TwitterException) {
                Toast.makeText(this@LoginActivity, "Login Failed !!!, Try after sometime", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButton.onActivityResult(requestCode, resultCode, data)
    }
}
