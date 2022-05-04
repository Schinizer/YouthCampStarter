package com.youthcamp.starter.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Singleton so we do not recreate multiple instances
 *
 * Network code are expensive so we try to keep a minimal instance of them
 */
object NetworkModule {
    // Create and config an instance of retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://worldtimeapi.org/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    // Create our API
    val worldTimeAPI: WorldTimeAPI = retrofit.create(WorldTimeAPI::class.java)
}