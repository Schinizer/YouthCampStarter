package com.youthcamp.starter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.youthcamp.starter.network.NetworkModule
import com.youthcamp.starter.network.WorldTime
import com.youthcamp.starter.network.WorldTimeAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * [MainActivity] where our code starts to run
 */
class MainActivity : AppCompatActivity() {
    // API
    private val worldTimeAPI: WorldTimeAPI = NetworkModule.worldTimeAPI

    // View references, lazily initialized
    private val title: TextView by lazy { findViewById(R.id.title) }
    private val description: TextView by lazy { findViewById(R.id.description) }
    private val fab: FloatingActionButton by lazy { findViewById(R.id.fab) }

    // Date format
    private val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sets our activity to this specific layout
        setContentView(R.layout.activity_main)

        // Set what happens when we tap on our fab
        fab.setOnClickListener { getData() }

        // Makes an initial network call
        getData()
    }

    private fun getData() {
        // Asynchronous network call through enqueue
        worldTimeAPI.getWorldTimeBasedOnIP()
            .enqueue(object : Callback<WorldTime> {
                override fun onResponse(call: Call<WorldTime>, response: Response<WorldTime>) {
                    val worldTime = response.body() ?: return // null check
                    val unixSeconds = TimeUnit.SECONDS.toMillis(worldTime.unixtime) // API returns in seconds
                    val formattedTime = dateFormat.format(Date(unixSeconds)) // Date() requires milliseconds

                    // Set our text view with string formatting
                    title.text = getString(R.string.time_now, formattedTime)
                    description.text = getString(R.string.ip_addr, worldTime.clientIPV4)
                }

                override fun onFailure(call: Call<WorldTime>, t: Throwable) {
                    Log.e("MainActivity", "getData onFailure()", t)
                }
            })
    }
}